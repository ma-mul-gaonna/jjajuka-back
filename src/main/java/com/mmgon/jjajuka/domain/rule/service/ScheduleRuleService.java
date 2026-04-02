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
        if (scheduleRuleRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("이미 존재하는 규칙 이름입니다: " + request.name());
        }
        validate(request);
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
                request.name(),
                request.minStaffPerShift(),
                request.maxStaffPerShift(),
                request.maxConsecutiveDays()
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
        if (request.minStaffPerShift() > request.maxStaffPerShift()) {
            throw new IllegalArgumentException("최소 인원이 최대 인원보다 클 수 없습니다.");
        }
        if (request.maxConsecutiveDays() < 1) {
            throw new IllegalArgumentException("최대 연속 근무일은 1일 이상이어야 합니다.");
        }
    }
}
