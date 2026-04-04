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
    private List<String> userRequests;
}
