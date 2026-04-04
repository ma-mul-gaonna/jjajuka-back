package com.mmgon.jjajuka.domain.swap.repository;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Integer> {
    Optional<Swap> findByRequesterScheduleIdAndStatus(Integer scheduleId, SwapStatus status);
    int countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(SwapStatus status, LocalDate start, LocalDate end);
}
