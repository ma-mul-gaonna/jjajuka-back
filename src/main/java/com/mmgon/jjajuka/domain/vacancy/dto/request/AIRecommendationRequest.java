package com.mmgon.jjajuka.domain.vacancy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendationRequest {

    @JsonProperty("input_json")
    private InputJsonDto inputJson;

    @JsonProperty("current_schedule")
    private CurrentScheduleDto currentSchedule;

    private AbsenceDto absence;

    @JsonProperty("user_request")
    private List<String> userRequest;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputJsonDto {
        private String startDate;
        private String endDate;
        private RulesDto rules;
        private List<EmployeeDto> employees;
        private List<ShiftDto> shifts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RulesDto {
        private int minRestHours;
        private int maxConsecutiveDays;
        private int maxShiftsPerDay;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeDto {
        private Long userId;
        private String userName;
        private List<String> roles;
        private List<String> skills;
        private List<String> preferredShifts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShiftDto {
        private String name;
        private String startTime;
        private String endTime;
        private int requiredCount;
        private List<String> requiredRoles;
        private List<String> requiredSkills;
        private boolean isNight;
        private List<MinSkillCoverageDto> minSkillCoverage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinSkillCoverageDto {
        private String skill;
        private int count;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentScheduleDto {
        private List<AssignmentDto> assignments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignmentDto {
        private String date;
        private Long userId;
        private String userName;
        private String shiftName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AbsenceDto {
        private Long userId;
        private String date;
        private String shiftName;
    }
}