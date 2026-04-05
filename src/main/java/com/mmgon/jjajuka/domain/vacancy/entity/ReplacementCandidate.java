package com.mmgon.jjajuka.domain.vacancy.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.RecommendStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "replacement_candidate")
@Getter
@NoArgsConstructor
public class ReplacementCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_member_id", nullable = false)
    private Member candidateMember;

    @Column(name = "candidate_rank")
    private Integer candidateRank;

    @Column(length = 255)
    private String reason;

    @Column(name = "is_selected")
    private Boolean isSelected;

    @Column(name = "score")
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RecommendStatus status;

    public void accept() {
        this.status = RecommendStatus.APPROVED;
    }

    public void reject() {
        this.status = RecommendStatus.REJECTED;
    }

    @Builder
    public ReplacementCandidate(Vacancy vacancy, Member candidateMember, Integer candidateRank, String reason, Boolean isSelected, Integer score) {
        this.vacancy = vacancy;
        this.candidateMember = candidateMember;
        this.candidateRank = candidateRank;
        this.reason = reason;
        this.isSelected = isSelected;
        this.score = score;
        this.status = RecommendStatus.DEFAULT;
    }
}
