package com.mmgon.jjajuka.domain.schedule.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InputJson {
    private String startDate;
    private String endDate;
    private Rules rules;
    private List<MemberDto> employees;
    private List<ShiftDto> shifts;

    @Getter
    @Builder
    public static class Rules {
        private int minRestHours;
        private int maxConsecutiveDays;
        private int maxShiftsPerDay;
    }

    @Getter
    @Builder
    public static class MemberDto {
        private Integer userId;
        private String userName;
        private List<String> roles;
        private List<String> skills;
        private List<String> preferredShifts;
    }

    @Getter
    @Builder
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

    @Getter
    @Builder
    public static class MinSkillCoverageDto {
        private String skill;
        private int count;
    }
}
