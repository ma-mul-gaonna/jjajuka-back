package com.mmgon.jjajuka.domain.vacancy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private Integer vacancyId;
    private Integer scheduleId;
    private String vacancyMemberName;
    private String status;
    private String message;
    private AbsenceDto absence;
    private List<RecommendationDto> recommendations;
    private List<Object> warnings;

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
        @JsonAlias("user_id")
        private Long userId;
        @JsonAlias("user_name")
        private String userName;
        private int score;
        private String reasons;
    }
}