package com.mmgon.jjajuka.domain.schedule.dto.response;

import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.global.enums.ScheduleStatus;
import com.mmgon.jjajuka.global.enums.ScheduleType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkScheduleResponse {

    private final Integer id;
    private final LocalDate workDate;
    private final ScheduleType shiftType;
    private final ScheduleStatus status;

    public WorkScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.workDate = schedule.getWorkDate();
        this.shiftType = schedule.getShiftType();
        this.status = schedule.getStatus();
    }
}
