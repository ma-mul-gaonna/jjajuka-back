package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.ReplacementCandidate;
import com.mmgon.jjajuka.global.enums.RecommendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplacementCandidateRepository extends JpaRepository<ReplacementCandidate, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ReplacementCandidate rc WHERE rc.vacancy.id = :vacancyId")
    void deleteByVacancyId(@Param("vacancyId") Integer vacancyId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ReplacementCandidate rc WHERE rc.vacancy.schedule.scheduleGroup.id = :scheduleGroupId")
    void deleteAllByScheduleGroupId(@Param("scheduleGroupId") Integer scheduleGroupId);

    boolean existsByVacancyId(Integer vacancyId);

    List<ReplacementCandidate> findByVacancyIdOrderByCandidateRankAsc(Integer vacancyId);

    List<ReplacementCandidate> findByVacancyIdAndCandidateMemberId(Integer vacancyId, Integer candidateMemberId);

    @Modifying
    @Query("update ReplacementCandidate rc set rc.status = :status where rc.id = :id")
    void updateStatus(@Param("id") Integer id, @Param("status") RecommendStatus status);
}