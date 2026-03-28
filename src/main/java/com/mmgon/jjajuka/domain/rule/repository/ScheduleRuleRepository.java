package com.mmgon.jjajuka.domain.rule.repository;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Integer> {
    boolean existsByName(String name);

}
