package com.mmgon.jjajuka.domain.schedule.repository;

import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByScheduleGroupId(Integer scheduleGroupId);

    List<Schedule> findByMemberIdAndWorkDateBetweenOrderByWorkDate(
            Integer memberId, LocalDate startDate, LocalDate endDate);

    List<Schedule> findAllByScheduleGroupIdOrderByWorkDateAscIdAsc(Integer scheduleGroupId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteByScheduleGroupId(Integer scheduleGroupId);
}
