package com.mmgon.jjajuka.domain.schedule.Mapper;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.schedule.controller.request.AiScheduleRequest;
import com.mmgon.jjajuka.domain.schedule.controller.request.InputJson;
import com.mmgon.jjajuka.domain.schedule.controller.request.ScheduleGenerateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class AiScheduleRequestMapper {

    private final DefaultShiftPolicy defaultShiftPolicy;

    public AiScheduleRequestMapper(DefaultShiftPolicy defaultShiftPolicy) {
        this.defaultShiftPolicy = defaultShiftPolicy;
    }

    public AiScheduleRequest toRequest(
            String scheduleYearMonth,
            ScheduleRule rule,
            List<Member> members,
            List<Dayoff> dayoffs,
            List<ScheduleGenerateRequest.ShiftRequest> shiftRequests,
            List<String> userRequests,
            List<String> ruleCustomValues
    ) {
        YearMonth yearMonth = YearMonth.parse(scheduleYearMonth);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<InputJson.MemberDto> employees = members.stream()
                .map(this::toMemberDto)
                .toList();

        List<InputJson.ShiftDto> shifts =
                (shiftRequests == null || shiftRequests.isEmpty())
                        ? defaultShiftPolicy.createDefaultShifts(rule.getRequiredCount())
                        : shiftRequests.stream()
                        .map(this::toShiftDto)
                        .toList();

        List<String> mergedUserRequests = new ArrayList<>();
        if (userRequests != null) {
            mergedUserRequests.addAll(userRequests);
        }

        for (Dayoff dayoff : dayoffs) {
            mergedUserRequests.add(dayoff.getMember().getName() + "는 " + dayoff.getDate() + " 쉬게 해줘");
        }

        if (ruleCustomValues != null) {
            mergedUserRequests.addAll(ruleCustomValues);
        }

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
        if (member.getSkills() != null) {
            skills.add(member.getSkills().name());
        }

        List<String> preferredShifts = member.getPreferredShifts() != null
                ? List.of(convertShiftName(member.getPreferredShifts().name()))
                : List.of();

        return InputJson.MemberDto.builder()
                .userId(member.getId())
                .userName(member.getName())
                .roles(roles)
                .skills(skills)
                .preferredShifts(preferredShifts)
                .build();
    }

    private String convertShiftName(String shiftName) {
        return switch (shiftName) {
            case "DAY" -> "Day";
            case "EVENING" -> "Evening";
            case "NIGHT" -> "Night";
            default -> shiftName;
        };
    }

    private InputJson.ShiftDto toShiftDto(ScheduleGenerateRequest.ShiftRequest shiftRequest) {
        return InputJson.ShiftDto.builder()
                .name(convertShiftName(shiftRequest.getName()))
                .startTime(shiftRequest.getStartTime())
                .endTime(shiftRequest.getEndTime())
                .requiredCount(shiftRequest.getRequiredCount())
                .requiredRoles(
                        shiftRequest.getRequiredRoles() != null
                                ? shiftRequest.getRequiredRoles()
                                : List.of()
                )
                .requiredSkills(
                        shiftRequest.getRequiredSkills() != null
                                ? shiftRequest.getRequiredSkills()
                                : List.of()
                )
                .isNight(shiftRequest.isNight())
                .minSkillCoverage(
                        shiftRequest.getMinSkillCoverage() != null
                                ? shiftRequest.getMinSkillCoverage().stream()
                                .map(msc -> InputJson.MinSkillCoverageDto.builder()
                                        .skill(msc.getSkill())
                                        .count(msc.getCount())
                                        .build())
                                .toList()
                                : List.of()
                )
                .build();
    }
}
