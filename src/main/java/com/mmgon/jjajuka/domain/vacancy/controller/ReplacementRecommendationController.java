package com.mmgon.jjajuka.domain.vacancy.controller;

import com.mmgon.jjajuka.domain.vacancy.dto.response.RecommendationResponse;
import com.mmgon.jjajuka.domain.vacancy.service.ReplacementRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replacement-recommendations")
public class ReplacementRecommendationController {

    private final ReplacementRecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> recommendations(@RequestParam Integer vacancyId) {

        RecommendationResponse response = recommendationService.recommend(vacancyId);

        return ResponseEntity.ok(response);
    }
}