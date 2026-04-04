package com.mmgon.jjajuka.domain.schedule.controller.request;

import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleGenerateRequest {
    private String scheduleYearMonth;
    private String reason;
    private ScheduleRuleDto.Request rule;
    private List<ShiftRequest> shifts;
    private List<String> userRequests;

    @Getter
    @NoArgsConstructor
    public static class ShiftRequest {
        private String name;
        private String startTime;
        private String endTime;
        private int requiredCount;
        private List<String> requiredRoles;
        private List<String> requiredSkills;
        private boolean isNight;
        private List<MinSkillCoverageRequest> minSkillCoverage;
    }

    @Getter
    @NoArgsConstructor
    public static class MinSkillCoverageRequest {
        private String skill;
        private int count;
    }
}
