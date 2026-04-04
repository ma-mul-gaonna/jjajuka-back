package com.mmgon.jjajuka.domain.swap.service;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.service.dto.DiscordEmbed;
import com.mmgon.jjajuka.domain.swap.service.dto.DiscordWebhookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordNotificationService {

    @Value("${discord.webhook.url:}")
    private String webhookUrl;

    @Value("${server.base-url:http://localhost:8888}")
    private String serverBaseUrl;

    private final WebClient webClient = WebClient.create();

    public DiscordWebhookRequest sendSwapRequestNotification(Swap swap) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.warn("Discord webhook URL is not configured. Skipping notification.");
            return null;
        }

        try {
            DiscordWebhookRequest request = buildSwapRequestMessage(swap);
            webClient.post()
                    .uri(webhookUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe();
            log.info("Discord notification sent successfully for swap ID: {}", swap.getId());
            return request;
        } catch (Exception e) {
            log.error("Failed to send Discord notification for swap ID: {}", swap.getId(), e);
            return null;
        }
    }

    private DiscordWebhookRequest buildSwapRequestMessage(Swap swap) {

        String requester = swap.getRequester().getName();
        String target = swap.getTarget().getName();
        String workDate = swap.getRequesterSchedule()
                .getWorkDate()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
        String shiftType = swap.getRequesterSchedule()
                .getShiftType()
                .toString();

        String responseUrl = String.format("%s/worker/requests", serverBaseUrl);

        String description = String.format(
                "'%s' 님에게 교대 요청이 도착했습니다.\n\n" +
                        "> 요청자: %s\n" +
                        "> 근무 일자: %s (%s)\n\n" +
                        "[응답페이지로 이동하기](%s)",
                target, requester, workDate, shiftType, responseUrl
        );

        DiscordEmbed embed = DiscordEmbed.builder()
                .title("교대 근무 요청")
                .description(description)
                .color(3381759)
                .build();

        return DiscordWebhookRequest.builder()
                .content("새로운 교대 근무 요청이 도착했어요!")
                .username("짜주까 알림봇")
                .embeds(List.of(embed))
                //.avatar_url("") TODO: Logo 생성 시 추가 필요
                .build();
    }
}
