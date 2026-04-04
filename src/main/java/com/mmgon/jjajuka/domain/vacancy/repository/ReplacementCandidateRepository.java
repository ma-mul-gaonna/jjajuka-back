package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.ReplacementCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplacementCandidateRepository extends JpaRepository<ReplacementCandidate, Integer> {
    void deleteByVacancyId(Integer vacancyId);
}