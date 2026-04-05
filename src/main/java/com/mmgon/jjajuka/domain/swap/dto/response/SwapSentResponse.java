package com.mmgon.jjajuka.domain.swap.dto.response;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.ScheduleStatus;
import com.mmgon.jjajuka.global.enums.ScheduleType;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SwapSentResponse {

    private Integer swapRequestId;
    private SwapStatus status;
    private LocalDate requestedAt;

    private Target target;
    private RequestSchedule requestSchedule;

    public static SwapSentResponse from(Swap swap) {
        return SwapSentResponse.builder()
                .swapRequestId(swap.getId())
                .status(swap.getStatus())
                .requestedAt(swap.getCreatedAt())
                .target(Target.from(swap.getTarget()))
                .requestSchedule(RequestSchedule.from(swap.getRequesterSchedule()))
                .build();
    }

    @Getter
    @Builder
    public static class Target {
        private Integer id;
        private String name;

        public static Target from(Member target) {
            return Target.builder()
                    .id(target.getId())
                    .name(target.getName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RequestSchedule {
        private Integer scheduleId;
        private LocalDate workDate;
        private ScheduleType shiftType;
        private ScheduleStatus scheduleStatus;

        public static RequestSchedule from(Schedule schedule) {
            return RequestSchedule.builder()
                    .scheduleId(schedule.getId())
                    .workDate(schedule.getWorkDate())
                    .shiftType(schedule.getShiftType())
                    .scheduleStatus(schedule.getStatus())
                    .build();
        }
    }
}
