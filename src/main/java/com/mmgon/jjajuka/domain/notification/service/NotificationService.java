package com.mmgon.jjajuka.domain.notification.service;

import com.mmgon.jjajuka.domain.notification.dto.NotificationDto;
import com.mmgon.jjajuka.domain.notification.controller.response.NotificationListResponse;
import com.mmgon.jjajuka.domain.notification.entity.Notification;
import com.mmgon.jjajuka.domain.notification.exception.NotificationErrorCode;
import com.mmgon.jjajuka.domain.notification.exception.NotificationException;
import com.mmgon.jjajuka.domain.notification.repository.NotificationRepository;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.service.dto.DiscordWebhookRequest;
import com.mmgon.jjajuka.global.enums.NotiType;
import com.mmgon.jjajuka.global.enums.ReferenceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
    }

    public NotificationListResponse getNotificationsByReceiverId(Integer receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId);

        List<NotificationDto> notificationDtos = notifications.stream()
                .map(NotificationDto::from)
                .collect(Collectors.toList());

        Integer unreadCount = (int) notifications.stream()
                .filter(notification -> !notification.getIsRead())
                .count();

        return NotificationListResponse.builder()
                .success(true)
                .data(NotificationListResponse.DataWrapper.builder()
                        .notifications(notificationDtos)
                        .unreadCount(unreadCount)
                        .build())
                .build();
    }

    @Transactional
    public Notification markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));

        notification.markAsRead();
        return notification;
    }

    @Transactional
    public Notification createSwapNotification(Swap swap, DiscordWebhookRequest message) {
        Notification notification = Notification.builder()
                .receiver(swap.getTarget())
                .title(message.getEmbeds().getFirst().getTitle())
                .message(message.getEmbeds().getFirst().getDescription())
                .referenceType(ReferenceType.SWAP)
                .referenceId(swap.getId())
                .notiType(NotiType.SWAP_REQUEST)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }
}
