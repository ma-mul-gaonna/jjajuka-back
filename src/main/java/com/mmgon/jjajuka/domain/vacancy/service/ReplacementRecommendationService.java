package com.mmgon.jjajuka.domain.vacancy.service;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.rule.entity.RuleCustom;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.rule.repository.RuleCustomRepository;
import com.mmgon.jjajuka.domain.rule.repository.ScheduleRuleRepository;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.repository.ScheduleRepository;
import com.mmgon.jjajuka.domain.vacancy.dto.request.AIRecommendationRequest;
import com.mmgon.jjajuka.domain.vacancy.dto.response.RecommendationResponse;
import com.mmgon.jjajuka.domain.vacancy.entity.ReplacementCandidate;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyException;
import com.mmgon.jjajuka.domain.vacancy.repository.ReplacementCandidateRepository;
import com.mmgon.jjajuka.domain.vacancy.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static com.mmgon.jjajuka.domain.vacancy.exception.VacancyErrorCode.VACANCY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplacementRecommendationService {

    private final VacancyRepository vacancyRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleRuleRepository scheduleRuleRepository;
    private final ReplacementCandidateRepository candidateRepository;
    private final MemberRepository memberRepository;
    private final RuleCustomRepository ruleCustomRepository;
    private final RestTemplate restTemplate;

    @Value("${ai.base-url}")
    private String aiBaseUrl;

    public RecommendationResponse recommend(Integer vacancyId) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new VacancyException(VACANCY_NOT_FOUND));

        Integer scheduleGroupId = vacancy.getSchedule().getScheduleGroup().getId();
        List<Schedule> schedules = scheduleRepository.findByScheduleGroupId(scheduleGroupId);
        List<Member> members = memberRepository.findAll();

        ScheduleRule rule = scheduleRuleRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("ScheduleRule이 없습니다."));

        List<RuleCustom> ruleCustom = ruleCustomRepository.findByScheduleRuleId(rule.getId());

        AIRecommendationRequest request = createAiRequest(vacancy, schedules, members, rule, ruleCustom);

        String url = aiBaseUrl + "/api/recommend-replacement";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AIRecommendationRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<RecommendationResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                RecommendationResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalStateException("AI 서버 호출에 실패했습니다.");
        }

        candidateRepository.deleteByVacancyId(vacancyId);

        List<ReplacementCandidate> candidates = response.getBody().getRecommendations().stream()
                .map(r -> {
                    Member member = memberRepository.findById(r.getUserId().intValue())
                            .orElseThrow(() -> new IllegalStateException("Member가 없습니다."));
                    return ReplacementCandidate.builder()
                            .vacancy(vacancy)
                            .candidateMember(member)
                            .candidateRank(r.getRank())
                            .reason(String.join(", ", r.getReasons()))
                            .isSelected(false)
                            .build();
                }).toList();

        candidateRepository.saveAll(candidates);

        return response.getBody();
    }

    private AIRecommendationRequest createAiRequest(
            Vacancy vacancy,
            List<Schedule> schedules,
            List<Member> members,
            ScheduleRule rule,
            List<RuleCustom> ruleCustoms
    ) {

        return AIRecommendationRequest.builder()
                .inputJson(createInputJson(schedules, members, rule))
                .currentSchedule(createCurrentSchedule(schedules))
                .absence(createAbsence(vacancy))
                .userRequest(ruleCustoms.stream()
                        .map(RuleCustom::getCustomValue)
                        .toList())
                .build();
    }

    private AIRecommendationRequest.InputJsonDto createInputJson(
            List<Schedule> schedules,
            List<Member> members,
            ScheduleRule rule
    ) {

        return AIRecommendationRequest.InputJsonDto.builder()
                .startDate(schedules.stream()
                        .map(Schedule::getWorkDate)
                        .min(LocalDate::compareTo)
                        .get()
                        .toString())
                .endDate(schedules.stream()
                        .map(Schedule::getWorkDate)
                        .max(LocalDate::compareTo)
                        .get()
                        .toString())
                .rules(createRules(rule))
                .employees(createEmployees(members))
                .shifts(createShifts())
                .build();
    }

    private AIRecommendationRequest.RulesDto createRules(ScheduleRule rule) {
        return AIRecommendationRequest.RulesDto.builder()
                .minRestHours(rule.getMinRestHours())
                .maxConsecutiveDays(rule.getMaxConsecutiveDays())
                .maxShiftsPerDay(rule.getMaxShiftsPerDay())
                .build();
    }

    private List<AIRecommendationRequest.EmployeeDto> createEmployees(List<Member> members) {
        return members.stream()
                .map(member -> AIRecommendationRequest.EmployeeDto.builder()
                        .userId(member.getId().longValue())
                        .userName(member.getName())
                        .roles(member.getPosition() != null ? List.of(member.getPosition()) : List.of())
                        .skills(member.getSkills() != null ? List.of(member.getSkills().name()) : List.of())
                        .preferredShifts(member.getPreferredShifts() != null
                                ? List.of(member.getPreferredShifts().name())
                                : List.of())
                        .build())
                .toList();
    }

    private List<AIRecommendationRequest.ShiftDto> createShifts() {
        return List.of(
                AIRecommendationRequest.ShiftDto.builder()
                        .name("Day")
                        .startTime("07:00")
                        .endTime("15:00")
                        .requiredCount(2)
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(false)
                        .minSkillCoverage(List.of(
                                new AIRecommendationRequest.MinSkillCoverageDto("GRADE_A", 1)
                        ))
                        .build(),
                AIRecommendationRequest.ShiftDto.builder()
                        .name("Evening")
                        .startTime("15:00")
                        .endTime("23:00")
                        .requiredCount(2)
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(false)
                        .minSkillCoverage(List.of(
                                new AIRecommendationRequest.MinSkillCoverageDto("GRADE_A", 1)
                        ))
                        .build(),
                AIRecommendationRequest.ShiftDto.builder()
                        .name("Night")
                        .startTime("23:00")
                        .endTime("07:00")
                        .requiredCount(2)
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(true)
                        .minSkillCoverage(List.of(
                                new AIRecommendationRequest.MinSkillCoverageDto("GRADE_A", 1)
                        ))
                        .build()
        );
    }

    private AIRecommendationRequest.CurrentScheduleDto createCurrentSchedule(List<Schedule> schedules) {

        List<AIRecommendationRequest.AssignmentDto> assignments = schedules.stream()
                .map(s -> AIRecommendationRequest.AssignmentDto.builder()
                        .date(s.getWorkDate().toString())
                        .userId(s.getMember().getId().longValue())
                        .userName(s.getMember().getName())
                        .shiftName(s.getShiftType().name())
                        .build())
                .toList();

        return new AIRecommendationRequest.CurrentScheduleDto(assignments);
    }

    private AIRecommendationRequest.AbsenceDto createAbsence(Vacancy vacancy) {
        return AIRecommendationRequest.AbsenceDto.builder()
                .userId(vacancy.getMember().getId().longValue())
                .date(vacancy.getSchedule().getWorkDate().toString())
                .shiftName(vacancy.getSchedule().getShiftType().name())
                .build();
    }
}