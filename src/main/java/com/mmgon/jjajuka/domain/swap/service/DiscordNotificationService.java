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

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordNotificationService {

    private final RestTemplate restTemplate;

    @Value("${discord.webhook.url:}")
    private String webhookUrl;

    @Value("${server.base-url:http://localhost:8888}")
    private String serverBaseUrl;

    public void sendSwapRequestNotification(Swap swap) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.warn("Discord webhook URL is not configured. Skipping notification.");
            return;
        }

        try {
            DiscordWebhookRequest request = buildSwapRequestMessage(swap);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DiscordWebhookRequest> entity = new HttpEntity<>(request, headers);

            restTemplate.postForEntity(webhookUrl, entity, String.class);
            log.info("Discord notification sent successfully for swap ID: {}", swap.getId());
        } catch (Exception e) {
            log.error("Failed to send Discord notification for swap ID: {}", swap.getId(), e);
        }
    }

    private DiscordWebhookRequest buildSwapRequestMessage(Swap swap) {
        String requesterName = swap.getRequester().getName();
        String targetName = swap.getTarget().getName();
        String workDate = swap.getRequesterSchedule().getWorkDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String shiftType = swap.getRequesterSchedule().getShiftType().toString();

        String acceptUrl = String.format("%s/api/shift-swap/%d/accept", serverBaseUrl, swap.getId());
        String rejectUrl = String.format("%s/api/shift-swap/%d/reject", serverBaseUrl, swap.getId());

        DiscordEmbed embed = DiscordEmbed.builder()
                .title("교대 근무 요청")
                .description("새로운 교대 근무 요청이 도착했습니다.")
                .color(5814783)
                .fields(List.of(
                        DiscordEmbed.Field.builder().name("요청자").value("관리자").inline(false).build(),
                        DiscordEmbed.Field.builder().name("대상자").value(targetName).inline(false).build(),
                        DiscordEmbed.Field.builder().name("근무 일자").value(workDate).inline(false).build(),
                        DiscordEmbed.Field.builder().name("근무 유형").value(shiftType).inline(false).build()
                ))
                .footer(DiscordEmbed.Footer.builder().text("짜주까 교대 근무 시스템").build())
                .build();

        DiscordWebhookRequest.Component actionRow = DiscordWebhookRequest.Component.builder()
                .type(1)
                .components(List.of(
                        DiscordWebhookRequest.ActionButton.builder()
                                .type(2)
                                .style(3)
                                .label("수락")
                                .url(acceptUrl)
                                .build(),
                        DiscordWebhookRequest.ActionButton.builder()
                                .type(2)
                                .style(4)
                                .label("거절")
                                .url(rejectUrl)
                                .build()
                ))
                .build();

        return DiscordWebhookRequest.builder()
                .embeds(List.of(embed))
                .components(List.of(actionRow))
                .build();
    }
}
