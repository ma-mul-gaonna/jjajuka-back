package com.mmgon.jjajuka.domain.schedule.service;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.domain.dayoff.repository.DayoffRepository;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.rule.repository.ScheduleRuleRepository;
import com.mmgon.jjajuka.domain.rule.service.ScheduleRuleService;
import com.mmgon.jjajuka.domain.schedule.Mapper.AiScheduleRequestMapper;
import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.request.ScheduleGenerateRequest;
import com.mmgon.jjajuka.domain.schedule.controller.response.AiScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleResponse;
import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.DayoffStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleGenerationFacade {

    private final ScheduleRuleService scheduleRuleService;
    private final ScheduleRuleRepository scheduleRuleRepository;
    private final MemberRepository memberRepository;
    private final DayoffRepository dayoffRepository;
    private final AiScheduleClient aiScheduleClient;
    private final ScheduleService scheduleService;
    private final AiScheduleRequestMapper aiScheduleRequestMapper;

    @Transactional
    public ScheduleResponse generateWithRules(ScheduleGenerateRequest request) {
        // 1. 규칙 저장
        ScheduleRuleDto.Response savedRuleDto = scheduleRuleService.create(request.getRule());

        ScheduleRule savedRule = scheduleRuleRepository.findWithRuleCustomsById(savedRuleDto.id())
                .orElseThrow(() -> new IllegalArgumentException("저장된 규칙을 찾을 수 없습니다."));

        List<String> ruleCustomValues = savedRule.getRuleCustoms().stream()
                .map(ruleCustom -> ruleCustom.getCustomValue())
                .toList();

        // 2. 사원 조회
        var members = memberRepository.findAllByAuthority(Authority.USER);

        // 3. 휴가 조회
        YearMonth yearMonth = YearMonth.parse(request.getScheduleYearMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Dayoff> approvedDayoffs =
                dayoffRepository.findByDateBetweenAndStatus(startDate, endDate, DayoffStatus.APPROVAL);

        // 4. AI 요청 생성
        AiScheduleRequest aiRequest = aiScheduleRequestMapper.toRequest(
                request.getScheduleYearMonth(),
                savedRule,
                members,
                approvedDayoffs,
                request.getShifts(),
                request.getUserRequests(),
                ruleCustomValues
        );

        // 5. AI 호출
        AiScheduleResponse aiResponse = aiScheduleClient.generate(aiRequest);

        // 6. 스케줄 저장
        Integer scheduleGroupId = scheduleService.saveGeneratedSchedules(
                request.getScheduleYearMonth(),
                request.getReason(),
                aiResponse
        );

        // 7. 저장된 결과를 프론트 조회 형식으로 반환
        return scheduleService.getSchedules(scheduleGroupId);
    }
}
