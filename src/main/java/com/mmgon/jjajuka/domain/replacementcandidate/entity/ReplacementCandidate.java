package com.mmgon.jjajuka.domain.replacementcandidate.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import jakarta.persistence.*;
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

    @Column(name = "rank")
    private Integer rank;

    @Column(length = 255)
    private String reason;

    @Column(name = "is_selected")
    private Boolean isSelected;
}
