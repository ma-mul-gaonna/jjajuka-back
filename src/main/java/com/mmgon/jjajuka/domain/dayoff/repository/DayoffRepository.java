package com.mmgon.jjajuka.domain.dayoff.repository;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.global.enums.DayoffStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DayoffRepository extends JpaRepository<Dayoff, Integer> {
    List<Dayoff> findByDateBetweenAndStatus(
            LocalDate startDate,
            LocalDate endDate,
            DayoffStatus status
    );
}
