package com.mmgon.jjajuka.domain.schedule.service;

import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.response.AiScheduleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiScheduleClient {

    private final RestTemplate restTemplate;

    @Value("${ai.base-url}")
    private String aiBaseUrl;

    public AiScheduleResponse generate(AiScheduleRequest request) {
        String url = aiBaseUrl + "/api/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Connection", "close");

        HttpEntity<AiScheduleRequest> httpEntity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<AiScheduleResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    AiScheduleResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalStateException("AI 서버 호출에 실패했습니다. status=" + response.getStatusCode());
            }

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("AI schedule API error. status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new IllegalStateException(
                    "AI 서버가 스케줄 생성 요청을 처리하지 못했습니다. status="
                            + e.getStatusCode().value()
                            + ", body="
                            + e.getResponseBodyAsString()
            );
        } catch (ResourceAccessException e) {
            log.error("AI schedule API connection error. url={}", url, e);
            throw new IllegalStateException("AI 서버에 연결할 수 없습니다. url=" + url);
        }
    }
}
