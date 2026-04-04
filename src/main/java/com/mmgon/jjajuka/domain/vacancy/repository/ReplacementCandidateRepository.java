package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.ReplacementCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplacementCandidateRepository extends JpaRepository<ReplacementCandidate, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ReplacementCandidate rc WHERE rc.vacancy.id = :vacancyId")
    void deleteByVacancyId(@Param("vacancyId") Integer vacancyId);
}