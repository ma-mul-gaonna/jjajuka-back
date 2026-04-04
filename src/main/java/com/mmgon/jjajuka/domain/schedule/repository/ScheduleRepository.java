package com.mmgon.jjajuka.domain.schedule.repository;

import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAllByScheduleGroupIdOrderByWorkDateAscIdAsc(Integer scheduleGroupId);
}
