package com.mmgon.jjajuka.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AiScheduleClient {

    private final RestTemplate restTemplate;

//    @Value("${ai.base-url}")
//    private String aiBaseUrl;

//    public AiScheduleCreateResponse generate(AiScheduleCreateRequest request) {
//        String url = aiBaseUrl + "/api/schedule/work-schedules/generate";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<AiScheduleCreateRequest> httpEntity =
//                new HttpEntity<>(request, headers);
//
//        ResponseEntity<AiScheduleCreateResponse> response = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                httpEntity,
//                AiScheduleCreateResponse.class
//        );
//
//        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
//            throw new IllegalStateException("AI 서버 호출에 실패했습니다.");
//        }
//
//        return response.getBody();
//    }
}
