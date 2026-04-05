package com.mmgon.jjajuka.domain.vacancy.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.global.enums.VacancyStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vacancy")
@Getter
@NoArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VacancyStatus status;

    @Column(length = 255)
    private String reason;

    public void accept() {
        status = VacancyStatus.APPROVED;
    }

    public void reject() {
        status = VacancyStatus.REJECTED;
    }

    @Builder
    public Vacancy(Member member, Schedule schedule, LocalDate createdAt, VacancyStatus status, String reason) {
        this.member = member;
        this.schedule = schedule;
        this.createdAt = createdAt;
        this.status = status;
        this.reason = reason;
    }
}
