package com.mmgon.jjajuka.domain.schedule.repository;

import com.mmgon.jjajuka.domain.schedule.entity.ScheduleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleGroupRepository extends JpaRepository<ScheduleGroup, Integer> {
    Optional<ScheduleGroup> findTopByScheduleYearMonthOrderByIdDesc(String scheduleYearMonth);
}
