package com.mmgon.jjajuka.domain.swap.repository;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Integer> {
    Optional<Swap> findByRequesterScheduleIdAndStatus(Integer scheduleId, SwapStatus status);

    Optional<Swap> findByRequesterScheduleId(Integer scheduleId);

    @Query("SELECT s FROM Swap s " +
            "JOIN FETCH s.requester " +
            "JOIN FETCH s.target " +
            "JOIN FETCH s.requesterSchedule " +
            "ORDER BY s.createdAt DESC")
    List<Swap> findAllWithDetails();

    @Query("SELECT s FROM Swap s " +
            "JOIN FETCH s.requester " +
            "JOIN FETCH s.requesterSchedule " +
            "WHERE s.target.id = :memberId " +
            "ORDER BY s.createdAt DESC")
    List<Swap> findReceivedSwapsByMemberId(Integer memberId);

    @Query("SELECT s FROM Swap s " +
            "JOIN FETCH s.target " +
            "JOIN FETCH s.requesterSchedule " +
            "WHERE s.requester.id = :memberId " +
            "ORDER BY s.createdAt DESC")
    List<Swap> findSentSwapsByMemberId(Integer memberId);

    int countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(SwapStatus status, LocalDate start, LocalDate end);

}
