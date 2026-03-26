package com.mmgon.dutyflow.domain.dayoff.repository;

import com.mmgon.dutyflow.domain.dayoff.entity.Dayoff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayoffRepository extends JpaRepository<Dayoff, Integer> {
}
