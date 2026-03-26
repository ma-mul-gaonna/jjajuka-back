package com.mmgon.dutyflow.domain.swap.repository;

import com.mmgon.dutyflow.domain.swap.entity.Swap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwapRepository extends JpaRepository<Swap, Integer> {
}
