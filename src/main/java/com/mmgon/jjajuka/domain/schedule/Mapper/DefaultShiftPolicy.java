package com.mmgon.jjajuka.domain.schedule.Mapper;

import com.mmgon.jjajuka.domain.schedule.controller.request.InputJson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultShiftPolicy {

    public List<InputJson.ShiftDto> createDefaultShifts(int requiredCount) {
        return List.of(
                InputJson.ShiftDto.builder()
                        .name("Day")
                        .startTime("07:00")
                        .endTime("15:00")
                        .requiredCount(requiredCount)
                        .requiredRoles(List.of("nurse"))
                        .requiredSkills(List.of("GRADE_A"))
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
                        .requiredCount(requiredCount)
                        .requiredRoles(List.of("nurse"))
                        .requiredSkills(List.of())
                        .isNight(false)
                        .minSkillCoverage(List.of())
                        .build(),
                InputJson.ShiftDto.builder()
                        .name("Night")
                        .startTime("23:00")
                        .endTime("07:00")
                        .requiredCount(requiredCount)
                        .requiredRoles(List.of("nurse"))
                        .requiredSkills(List.of())
                        .isNight(true)
                        .minSkillCoverage(List.of())
                        .build()
        );
    }
}
