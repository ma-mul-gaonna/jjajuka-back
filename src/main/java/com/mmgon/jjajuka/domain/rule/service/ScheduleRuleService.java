package com.mmgon.dutyflow.domain.rule.service;

import com.mmgon.dutyflow.domain.rule.entity.ScheduleRule;
import com.mmgon.dutyflow.domain.rule.repository.ScheduleRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleRuleService {

    private final ScheduleRuleRepository scheduleRuleRepository;

    public List<ScheduleRule> findAll() {
        return scheduleRuleRepository.findAll();
    }

    public ScheduleRule findById(Integer id) {
        return scheduleRuleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ScheduleRule not found: " + id));
    }
}
