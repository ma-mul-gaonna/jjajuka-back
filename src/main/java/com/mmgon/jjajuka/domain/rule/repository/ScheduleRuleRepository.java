package com.mmgon.jjajuka.domain.rule.repository;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Integer> {

    @Query("""
            select distinct sr
            from ScheduleRule sr
            left join fetch sr.ruleCustoms rc
            where sr.id = :id
            """)
    Optional<ScheduleRule> findWithRuleCustomsById(@Param("id") Integer id);
}
