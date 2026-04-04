package com.mmgon.jjajuka.domain.vacancy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private String status;
    private String message;
    private AbsenceDto absence;
    private List<RecommendationDto> recommendations;
    private List<String> warnings;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AbsenceDto {
        private Long userId;
        private String date;
        private String shiftName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendationDto {
        private int rank;
        private Long userId;
        private String userName;
        private int score;
        private String reasons;
    }
}