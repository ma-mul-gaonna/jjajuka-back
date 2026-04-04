package com.mmgon.jjajuka.domain.rule.repository;

import com.mmgon.jjajuka.domain.rule.entity.RuleCustom;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RuleCustomRepository extends JpaRepository<RuleCustom, Integer> {
    List<RuleCustom> findByScheduleRuleId(Integer scheduleRuleId);
}
