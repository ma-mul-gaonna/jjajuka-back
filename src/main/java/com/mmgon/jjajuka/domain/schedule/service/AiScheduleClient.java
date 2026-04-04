package com.mmgon.jjajuka.domain.schedule.service;

import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.response.AiScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

        HttpEntity<AiScheduleRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<AiScheduleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                AiScheduleResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalStateException("AI 서버 호출에 실패했습니다.");
        }

        return response.getBody();
    }
}
