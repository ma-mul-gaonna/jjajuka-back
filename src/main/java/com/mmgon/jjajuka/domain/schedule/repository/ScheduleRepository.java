package com.mmgon.dutyflow.domain.schedule.repository;

import com.mmgon.dutyflow.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
