package com.mmgon.jjajuka.domain.rule.service;

import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.rule.repository.ScheduleRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleRuleService {

    private final ScheduleRuleRepository scheduleRuleRepository;

    public List<ScheduleRuleDto.Response> getAll() {
        return scheduleRuleRepository.findAll().stream()
                .map(ScheduleRuleDto.Response::from)
                .toList();
    }

    public ScheduleRuleDto.Response getOne(int id) {
        return ScheduleRuleDto.Response.from(findById(id));
    }

    @Transactional
    public ScheduleRuleDto.Response create(ScheduleRuleDto.Request request) {
        validate(request);
        scheduleRuleRepository.deleteAll();
        scheduleRuleRepository.flush();
        ScheduleRule scheduleRule = request.toEntity();
        if (request.customValues() != null) {
            request.customValues().forEach(scheduleRule::addRuleCustom);
        }
        return ScheduleRuleDto.Response.from(scheduleRuleRepository.save(scheduleRule));
    }

    @Transactional
    public ScheduleRuleDto.Response update(int id, ScheduleRuleDto.Request request) {
        validate(request);
        ScheduleRule scheduleRule = findById(id);
        scheduleRule.update(
                request.minRestHours(),
                request.maxConsecutiveDays(),
                request.maxShiftsPerDay(),
                request.requiredCount()
        );
        return ScheduleRuleDto.Response.from(scheduleRule);
    }

    @Transactional
    public void delete(int id) {
        scheduleRuleRepository.delete(findById(id));
    }

    private ScheduleRule findById(int id) {
        return scheduleRuleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 규칙입니다: " + id));
    }

    private void validate(ScheduleRuleDto.Request request) {
        if (request.minRestHours() < 0) {
            throw new IllegalArgumentException("최소 휴식 시간은 0 이상이어야 합니다.");
        }
        if (request.maxConsecutiveDays() < 1) {
            throw new IllegalArgumentException("최대 연속 근무일은 1일 이상이어야 합니다.");
        }
        if (request.maxShiftsPerDay() < 1) {
            throw new IllegalArgumentException("일일 최대 근무 수는 1 이상이어야 합니다.");
        }
        if (request.requiredCount() < 1) {
            throw new IllegalArgumentException("필요 인원은 1 이상이어야 합니다.");
        }
    }
}
