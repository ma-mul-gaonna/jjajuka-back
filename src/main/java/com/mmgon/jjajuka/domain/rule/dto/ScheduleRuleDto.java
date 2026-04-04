package com.mmgon.jjajuka.domain.rule.dto;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;

import java.util.List;

public class ScheduleRuleDto {

    public record Request(
            int minRestHours,
            int maxConsecutiveDays,
            int maxShiftsPerDay,
            int requiredCount,
            List<String> customValues
    ) {
        public ScheduleRule toEntity() {
            return ScheduleRule.builder()
                    .minRestHours(minRestHours)
                    .maxConsecutiveDays(maxConsecutiveDays)
                    .maxShiftsPerDay(maxShiftsPerDay)
                    .requiredCount(requiredCount)
                    .build();
        }
    }

    public record CustomValueDto(Integer id, String value) {}

    public record Response(
            Integer id,
            int minRestHours,
            int maxConsecutiveDays,
            int maxShiftsPerDay,
            int requiredCount,
            List<CustomValueDto> customValues
    ) {
        public static Response from(ScheduleRule scheduleRule) {
            List<CustomValueDto> customValues = scheduleRule.getRuleCustoms().stream()
                    .map(rc -> new CustomValueDto(rc.getId(), rc.getCustomValue()))
                    .toList();
            return new Response(
                    scheduleRule.getId(),
                    scheduleRule.getMinRestHours(),
                    scheduleRule.getMaxConsecutiveDays(),
                    scheduleRule.getMaxShiftsPerDay(),
                    scheduleRule.getRequiredCount(),
                    customValues
            );
        }
    }
}
