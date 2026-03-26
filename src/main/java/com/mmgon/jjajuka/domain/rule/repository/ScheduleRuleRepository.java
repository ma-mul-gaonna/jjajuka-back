package com.mmgon.dutyflow.domain.rule.repository;

import com.mmgon.dutyflow.domain.rule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Integer> {
}
