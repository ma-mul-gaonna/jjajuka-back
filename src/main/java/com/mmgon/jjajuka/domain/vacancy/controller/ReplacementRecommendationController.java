package com.mmgon.jjajuka.domain.vacancy.controller;

import com.mmgon.jjajuka.domain.vacancy.dto.response.RecommendationResponse;
import com.mmgon.jjajuka.domain.vacancy.service.ReplacementRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replacement-recommendations")
public class ReplacementRecommendationController {

    private final ReplacementRecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<RecommendationResponse>> recommendations() {

        List<RecommendationResponse> responses = recommendationService.recommend();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/exist")
    public ResponseEntity<List<RecommendationResponse>> exist() {

        List<RecommendationResponse> responses = recommendationService.exist();

        return ResponseEntity.ok(responses);
    }


}