package com.mmgon.jjajuka.domain.schedule.Mapper;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.request.InputJson;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class AiScheduleRequestMapper {

    public AiScheduleRequest toRequest(
            String scheduleYearMonth,
            ScheduleRule rule,
            List<Member> members,
            List<Dayoff> dayoffs,
            List<String> userRequests
    ) {
        YearMonth yearMonth = YearMonth.parse(scheduleYearMonth);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<InputJson.MemberDto> employees = members.stream()
                .map(this::toMemberDto)
                .toList();

        List<InputJson.ShiftDto> shifts = List.of(
                InputJson.ShiftDto.builder()
                        .name("Day")
                        .startTime("07:00")
                        .endTime("15:00")
                        .requiredCount(rule.getRequiredCount())
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(false)
                        .minSkillCoverage(List.of(
                                InputJson.MinSkillCoverageDto.builder()
                                        .skill("GRADE_A")
                                        .count(1)
                                        .build()
                        ))
                        .build(),
                InputJson.ShiftDto.builder()
                        .name("Evening")
                        .startTime("15:00")
                        .endTime("23:00")
                        .requiredCount(rule.getRequiredCount())
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(false)
                        .minSkillCoverage(List.of(
                                InputJson.MinSkillCoverageDto.builder()
                                        .skill("GRADE_A")
                                        .count(1)
                                        .build()
                        ))
                        .build(),
                InputJson.ShiftDto.builder()
                        .name("Night")
                        .startTime("23:00")
                        .endTime("07:00")
                        .requiredCount(rule.getRequiredCount())
                        .requiredRoles(List.of())
                        .requiredSkills(List.of())
                        .isNight(true)
                        .minSkillCoverage(List.of(
                                InputJson.MinSkillCoverageDto.builder()
                                        .skill("GRADE_A")
                                        .count(1)
                                        .build()
                        ))
                        .build()
        );

        List<String> mergedUserRequests = new ArrayList<>();
        if (userRequests != null) {
            mergedUserRequests.addAll(userRequests);
        }

        // 휴가 승인된 사람은 자연어 제약으로 추가
        for (Dayoff dayoff : dayoffs) {
            mergedUserRequests.add(dayoff.getMember().getName() + "는 " + dayoff.getDate() + " 쉬게 해줘");
        }

        // rule.customValues 도 자연어 제약으로 추가 가능
        rule.getRuleCustoms().forEach(custom ->
                mergedUserRequests.add(custom.getCustomValue())
        );

        InputJson inputJson = InputJson.builder()
                .startDate(startDate.toString())
                .endDate(endDate.toString())
                .rules(InputJson.Rules.builder()
                        .minRestHours(rule.getMinRestHours())
                        .maxConsecutiveDays(rule.getMaxConsecutiveDays())
                        .maxShiftsPerDay(rule.getMaxShiftsPerDay())
                        .build())
                .employees(employees)
                .shifts(shifts)
                .build();

        return AiScheduleRequest.builder()
                .inputJson(inputJson)
                .userRequests(mergedUserRequests)
                .build();
    }

    private InputJson.MemberDto toMemberDto(Member member) {
        List<String> roles = List.of("nurse");

        List<String> skills = new ArrayList<>();
        if (member.getPosition() != null) {
            skills.add("GRADE_" + member.getSkills().name());
        }

        return InputJson.MemberDto.builder()
                .userId(member.getId())
                .userName(member.getName())
                .roles(roles)
                .skills(skills)
                .preferredShifts(List.of())
                .build();
    }
}
