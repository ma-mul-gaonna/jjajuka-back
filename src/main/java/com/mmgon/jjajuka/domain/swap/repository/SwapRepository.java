package com.mmgon.jjajuka.domain.swap.repository;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Integer> {
    Optional<Swap> findByRequesterScheduleIdAndStatus(Integer scheduleId, SwapStatus status);

    @Query("SELECT s FROM Swap s " +
            "JOIN FETCH s.requester " +
            "JOIN FETCH s.requesterSchedule " +
            "WHERE s.target.id = :memberId " +
            "ORDER BY s.createdAt DESC")
    List<Swap> findReceivedSwapsByMemberId(Integer memberId);

    int countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(SwapStatus status, LocalDate start, LocalDate end);

    boolean existsByRequesterSchedule_ScheduleGroup_IdOrTargetSchedule_ScheduleGroup_Id(
            Integer requesterScheduleGroupId,
            Integer targetScheduleGroupId
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Swap s WHERE s.requesterSchedule.scheduleGroup.id = :scheduleGroupId")
    void deleteAllByRequesterScheduleGroupId(@Param("scheduleGroupId") Integer scheduleGroupId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Swap s WHERE s.targetSchedule IS NOT NULL AND s.targetSchedule.scheduleGroup.id = :scheduleGroupId")
    void deleteAllByTargetScheduleGroupId(@Param("scheduleGroupId") Integer scheduleGroupId);
}
