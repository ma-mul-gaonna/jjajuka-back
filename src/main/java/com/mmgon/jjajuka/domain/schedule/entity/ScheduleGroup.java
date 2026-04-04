package com.mmgon.jjajuka.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule_group")
@Getter
@NoArgsConstructor
public class ScheduleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "schedule_year_month", length = 10)
    private String scheduleYearMonth;

    @Column(length = 255)
    private String reason;

    @Builder
    public ScheduleGroup(String scheduleYearMonth, String reason) {
        this.scheduleYearMonth = scheduleYearMonth;
        this.reason = reason;
    }

    public static ScheduleGroup create(String scheduleYearMonth, String reason) {
        return ScheduleGroup.builder()
                .scheduleYearMonth(scheduleYearMonth)
                .reason(reason)
                .build();
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }
}
