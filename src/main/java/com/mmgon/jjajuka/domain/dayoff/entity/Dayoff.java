package com.mmgon.dutyflow.domain.dayoff.entity;

import com.mmgon.dutyflow.domain.member.entity.Member;
import com.mmgon.dutyflow.global.enums.DayoffStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "dayoff")
@Getter
@NoArgsConstructor
public class Dayoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 255)
    private String type;

    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayoffStatus status;
}
