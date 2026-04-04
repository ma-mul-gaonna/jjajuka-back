package com.mmgon.jjajuka.domain.vacancy.dto.response;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VacancyDto {
    private Integer vacancyId;
    private Integer memberId;
    private String memberName;
    private Integer scheduleId;
    private ScheduleInfo schedule;
    private String reason;
    private String status;
    private String createdAt;

    @Getter
    @Builder
    public static class ScheduleInfo {
        private String workDate;
        private String shiftType;
        private String status;
    }

    public static VacancyDto from(Vacancy vacancy) {
        return VacancyDto.builder()
                .vacancyId(vacancy.getId())
                .memberId(vacancy.getMember().getId())
                .memberName(vacancy.getMember().getName())
                .scheduleId(vacancy.getSchedule().getId())
                .schedule(ScheduleInfo.builder()
                        .workDate(vacancy.getSchedule().getWorkDate().toString())
                        .shiftType(vacancy.getSchedule().getShiftType().name())
                        .status(vacancy.getSchedule().getStatus().name())
                        .build())
                .reason(vacancy.getReason())
                .status(vacancy.getStatus().name())
                .createdAt(vacancy.getCreatedAt().toString())
                .build();
    }
}
