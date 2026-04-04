package com.mmgon.jjajuka.domain.schedule.service;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.domain.dayoff.repository.DayoffRepository;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.rule.repository.ScheduleRuleRepository;
import com.mmgon.jjajuka.domain.rule.service.ScheduleRuleService;
import com.mmgon.jjajuka.domain.schedule.Mapper.AiScheduleRequestMapper;
import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.request.ScheduleGenerateRequest;
import com.mmgon.jjajuka.domain.schedule.controller.response.AiScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleGenerateResponse;
import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.DayoffStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public ScheduleGenerateResponse generateWithRules(ScheduleGenerateRequest request) {
        // 1. 규칙 저장
        ScheduleRuleDto.Response savedRuleDto = scheduleRuleService.create(request.getRule());

        ScheduleRule savedRule = scheduleRuleRepository.findById(savedRuleDto.id())
                .orElseThrow(() -> new IllegalArgumentException("저장된 규칙을 찾을 수 없습니다."));

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
                request.getUserRequests()
        );

        // 5. AI 호출
        AiScheduleResponse aiResponse = aiScheduleClient.generate(aiRequest);

        // 6. 스케줄 저장
        Integer scheduleGroupId = scheduleService.saveGeneratedSchedules(
                request.getScheduleYearMonth(),
                request.getReason(),
                aiResponse
        );

        // 7. 응답
        return ScheduleGenerateResponse.builder()
                .ruleId(savedRuleDto.id())
                .scheduleGroupId(scheduleGroupId)
                .scheduleYearMonth(request.getScheduleYearMonth())
                .message("근무표 규칙 저장 및 AI 근무표 생성이 완료되었습니다.")
                .build();
    }
}
