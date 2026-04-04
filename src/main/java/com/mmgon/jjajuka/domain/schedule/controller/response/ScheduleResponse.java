package com.mmgon.jjajuka.domain.schedule.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ScheduleResponse {

    private Integer scheduleGroupId;
    private String scheduleYearMonth;
    private List<ScheduleDayResponse> days;

    @Getter
    @Builder
    public static class ScheduleDayResponse {
        private LocalDate date;
        private int dayCount;
        private int eveningCount;
        private int nightCount;
        private List<ScheduleAssignmentResponse> assignments;
    }

    @Getter
    @Builder
    public static class ScheduleAssignmentResponse {
        private Integer scheduleId;
        private Integer memberId;
        private String memberName;
        private String shiftType;
        private String startTime;
        private String endTime;
    }
}
