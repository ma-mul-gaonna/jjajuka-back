package com.mmgon.jjajuka.domain.swap.dto.response;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.ScheduleType;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SwapAdminResponse {

    private Integer swapRequestId;
    private SwapStatus status;
    private LocalDate requestedAt;

    private MemberInfo requester;
    private MemberInfo target;
    private ScheduleInfo requestSchedule;

    public static SwapAdminResponse from(Swap swap) {
        return SwapAdminResponse.builder()
                .swapRequestId(swap.getId())
                .status(swap.getStatus())
                .requestedAt(swap.getCreatedAt())
                .requester(MemberInfo.from(swap.getRequester()))
                .target(MemberInfo.from(swap.getTarget()))
                .requestSchedule(ScheduleInfo.from(swap.getRequesterSchedule()))
                .build();
    }

    @Getter
    @Builder
    public static class MemberInfo {
        private Integer id;
        private String name;

        public static MemberInfo from(Member member) {
            return MemberInfo.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ScheduleInfo {
        private Integer scheduleId;
        private LocalDate workDate;
        private ScheduleType shiftType;

        public static ScheduleInfo from(Schedule schedule) {
            return ScheduleInfo.builder()
                    .scheduleId(schedule.getId())
                    .workDate(schedule.getWorkDate())
                    .shiftType(schedule.getShiftType())
                    .build();
        }
    }
}
