package com.mmgon.jjajuka.domain.schedule.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleGenerateResponse {
    private Integer ruleId;
    private Integer scheduleGroupId;
    private String scheduleYearMonth;
    private String message;
}
