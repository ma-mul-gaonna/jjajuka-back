package com.mmgon.jjajuka.domain.vacancy.service;

import com.mmgon.jjajuka.domain.notification.service.NotificationService;
import com.mmgon.jjajuka.domain.vacancy.dto.response.VacancyNotificationResponse;
import com.mmgon.jjajuka.domain.vacancy.event.VacancyCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancySseService {

    private static final Long SSE_TIMEOUT = 1800000L; // 30 minutes
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final AtomicInteger subscriberCounter = new AtomicInteger(0);
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    public SseEmitter subscribe(String sessionId) {
        String subscriberId = sessionId + "-" + subscriberCounter.incrementAndGet();
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        emitters.put(subscriberId, emitter);

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            log.error("Failed to send connect event to subscriber: {}", subscriberId, e);
            emitters.remove(subscriberId);
            emitter.completeWithError(e);
            return emitter;
        }

        emitter.onCompletion(() -> {
            emitters.remove(subscriberId);
            log.info("SSE connection completed for subscriber: {}, remaining: {}",
                    subscriberId, emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(subscriberId);
            log.info("SSE connection timed out for subscriber: {}, remaining: {}",
                    subscriberId, emitters.size());
        });

        emitter.onError((e) -> {
            emitters.remove(subscriberId);
            log.error("SSE connection error for subscriber: {}, remaining: {}",
                    subscriberId, emitters.size(), e);
        });

        return emitter;
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handleVacancyCreated(VacancyCreatedEvent event) {

        if (emitters.isEmpty()) {
            log.debug("No subscribers to broadcast vacancy update");
            return;
        }

        VacancyNotificationResponse notificationResponse = VacancyNotificationResponse.from(event.getVacancy());

        emitters.forEach((subscriberId, emitter) -> {
            try {
                String jsonData = objectMapper.writeValueAsString(notificationResponse);
                emitter.send(SseEmitter.event()
                        .name("vacancy-update")
                        .data(jsonData));
                log.debug("Sent vacancy-update event to subscriber: {}", subscriberId);
            } catch (IOException e) {
                log.error("Failed to send event to subscriber: {}, removing subscriber", subscriberId, e);
                emitters.remove(subscriberId);
                emitter.completeWithError(e);
            }
        });

        try {
            notificationService.createVacancyNotificationsForAdmins(event.getVacancy(), notificationResponse);
        } catch (Exception e) {
            log.error("Failed to create vacancy notifications for admins", e);
        }

    }
}
