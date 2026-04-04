package com.mmgon.jjajuka.domain.schedule.repository;

import com.mmgon.jjajuka.domain.schedule.entity.ScheduleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleGroupRepository extends JpaRepository<ScheduleGroup, Integer> {
    List<ScheduleGroup> findAllByScheduleYearMonthOrderByIdAsc(String scheduleYearMonth);
}
