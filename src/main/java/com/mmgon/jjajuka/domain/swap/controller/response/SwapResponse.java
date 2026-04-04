package com.mmgon.jjajuka.domain.swap.controller.response;

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
public class SwapResponse {

    private Integer swapRequestId;
    private SwapStatus status;
    private LocalDate requestedAt;

    private Requester requester;
    private RequestSchedule requestSchedule;

    public static SwapResponse from(Swap swap) {
        return SwapResponse.builder()
                .swapRequestId(swap.getId())
                .status(swap.getStatus())
                .requestedAt(swap.getCreatedAt())
                .requester(Requester.from(swap.getRequester()))
                .requestSchedule(RequestSchedule.from(swap.getRequesterSchedule()))
                .build();
    }

    @Getter
    @Builder
    public static class Requester {
        private Integer id;
        private String name;

        public static Requester from(Member requester) {
            return Requester.builder()
                    .id(requester.getId())
                    .name(requester.getName())
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