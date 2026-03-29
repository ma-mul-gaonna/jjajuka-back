package com.mmgon.jjajuka.domain.swap.repository;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Integer> {
    Optional<Swap> findByRequesterScheduleIdAndStatus(Integer scheduleId, SwapStatus status);
}
