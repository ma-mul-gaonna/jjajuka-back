package com.mmgon.jjajuka.domain.schedule.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.ScheduleStatus;
import com.mmgon.jjajuka.global.enums.ScheduleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_group_id", nullable = false)
    private ScheduleGroup scheduleGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType shiftType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    @Builder
    public Schedule(
            ScheduleGroup scheduleGroup,
            Member member,
            LocalDate workDate,
            ScheduleType shiftType,
            ScheduleStatus status
    ) {
        this.scheduleGroup = scheduleGroup;
        this.member = member;
        this.workDate = workDate;
        this.shiftType = shiftType;
        this.status = status;
    }

    public static Schedule create(
            ScheduleGroup scheduleGroup,
            Member member,
            LocalDate workDate,
            ScheduleType shiftType,
            ScheduleStatus status
    ) {
        return Schedule.builder()
                .scheduleGroup(scheduleGroup)
                .member(member)
                .workDate(workDate)
                .shiftType(shiftType)
                .status(status)
                .build();
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeStatus(ScheduleStatus status) {
        this.status = status;
    }
}
