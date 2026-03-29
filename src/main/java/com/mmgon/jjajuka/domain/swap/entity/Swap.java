package com.mmgon.jjajuka.domain.swap.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "swap")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Swap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private Member target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_schedule_id", nullable = false)
    private Schedule requesterSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_schedule_id", nullable = true)
    private Schedule targetSchedule;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwapStatus status;

    public static Swap createSwapRequest(Member requester, Member target, Schedule requesterSchedule) {
        return Swap.builder()
                .requester(requester)
                .target(target)
                .requesterSchedule(requesterSchedule)
                .targetSchedule(null)
                .status(SwapStatus.PENDING)
                .createdAt(LocalDate.now())
                .build();
    }
}
