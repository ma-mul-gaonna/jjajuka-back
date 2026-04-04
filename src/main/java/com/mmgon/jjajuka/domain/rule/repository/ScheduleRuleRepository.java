package com.mmgon.jjajuka.domain.rule.repository;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Integer> {

    @EntityGraph(attributePaths = "ruleCustoms")
    Optional<ScheduleRule> findWithRuleCustomsById(Integer id);
}