package com.mmgon.jjajuka.domain.schedule.repository;

import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
