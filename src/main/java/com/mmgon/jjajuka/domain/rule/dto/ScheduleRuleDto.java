package com.mmgon.jjajuka.domain.rule.dto;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;

import java.util.List;

public class ScheduleRuleDto {

    public record Request(
            String name,
            int minStaffPerShift,
            int maxStaffPerShift,
            int maxConsecutiveDays,
            List<String> customValues
    ) {
        public ScheduleRule toEntity() {
            return ScheduleRule.builder()
                    .name(name)
                    .minStaffPerShift(minStaffPerShift)
                    .maxStaffPerShift(maxStaffPerShift)
                    .maxConsecutiveDays(maxConsecutiveDays)
                    .build();
        }
    }

    public record Response(
            Integer id,
            String name,
            int minStaffPerShift,
            int maxStaffPerShift,
            int maxConsecutiveDays,
            List<String> customValues
    ) {
        public static Response from(ScheduleRule scheduleRule) {
            List<String> customValues = scheduleRule.getRuleCustoms().stream()
                    .map(rc -> rc.getCustomValue())
                    .toList();
            return new Response(
                    scheduleRule.getId(),
                    scheduleRule.getName(),
                    scheduleRule.getMinStaffPerShift(),
                    scheduleRule.getMaxStaffPerShift(),
                    scheduleRule.getMaxConsecutiveDays(),
                    customValues
            );
        }
    }
}
