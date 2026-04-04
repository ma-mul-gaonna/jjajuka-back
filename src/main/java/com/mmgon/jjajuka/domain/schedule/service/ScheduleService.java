package com.mmgon.jjajuka.domain.schedule.service;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.schedule.controller.response.AiScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.entity.ScheduleGroup;
import com.mmgon.jjajuka.domain.schedule.repository.ScheduleGroupRepository;
import com.mmgon.jjajuka.domain.schedule.repository.ScheduleRepository;
import com.mmgon.jjajuka.global.enums.ScheduleStatus;
import com.mmgon.jjajuka.global.enums.ScheduleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleGroupRepository scheduleGroupRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Integer id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found: " + id));
    }

    public List<Schedule> findMonthlyScheduleByMember(Integer memberId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return scheduleRepository.findByMemberIdAndWorkDateBetweenOrderByWorkDate(memberId, startDate, endDate);
  }
    @Transactional
    public Integer saveGeneratedSchedules(
            String scheduleYearMonth,
            String reason,
            AiScheduleResponse aiScheduleResponse
    ) {
        ScheduleGroup scheduleGroup = scheduleGroupRepository.save(
                ScheduleGroup.create(scheduleYearMonth, reason)
        );

        List<Schedule> schedules = aiScheduleResponse.getAssignments().stream()
                .map(assignment -> {
                    Member member = memberRepository.findById(assignment.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "해당 회원이 존재하지 않습니다. memberId=" + assignment.getUserId()
                            ));

                    return Schedule.create(
                            scheduleGroup,
                            member,
                            LocalDate.parse(assignment.getDate()),
                            toScheduleType(assignment.getShiftName()),
                            ScheduleStatus.ACTIVE
                    );
                })
                .toList();

        scheduleRepository.saveAll(schedules);

        return scheduleGroup.getId();
    }

    public ScheduleResponse getSchedules(Integer scheduleGroupId) {
        ScheduleGroup scheduleGroup = scheduleGroupRepository.findById(scheduleGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 근무표 그룹이 존재하지 않습니다. id=" + scheduleGroupId));

        List<Schedule> schedules = scheduleRepository.findAllByScheduleGroupIdOrderByWorkDateAscIdAsc(scheduleGroupId);

        Map<LocalDate, List<Schedule>> groupedByDate = schedules.stream()
                .filter(this::isVisibleSchedule)
                .collect(Collectors.groupingBy(Schedule::getWorkDate));

        YearMonth yearMonth = YearMonth.parse(scheduleGroup.getScheduleYearMonth());
        List<ScheduleResponse.ScheduleDayResponse> days = new ArrayList<>();

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            List<Schedule> daySchedules = groupedByDate.getOrDefault(date, List.of());

            int dayCount = countByShiftType(daySchedules, ScheduleType.DAY);
            int eveningCount = countByShiftType(daySchedules, ScheduleType.EVENING);
            int nightCount = countByShiftType(daySchedules, ScheduleType.NIGHT);

            List<ScheduleResponse.ScheduleAssignmentResponse> assignments = daySchedules.stream()
                    .sorted(Comparator
                            .comparingInt((Schedule schedule) -> getShiftOrder(schedule.getShiftType()))
                            .thenComparing(schedule -> schedule.getMember().getName())
                            .thenComparing(Schedule::getId))
                    .map(schedule -> ScheduleResponse.ScheduleAssignmentResponse.builder()
                            .scheduleId(schedule.getId())
                            .memberId(schedule.getMember().getId())
                            .memberName(schedule.getMember().getName())
                            .shiftType(schedule.getShiftType().name())
                            .startTime(getStartTime(schedule.getShiftType()))
                            .endTime(getEndTime(schedule.getShiftType()))
                            .build())
                    .toList();

            days.add(
                    ScheduleResponse.ScheduleDayResponse.builder()
                            .date(date)
                            .dayCount(dayCount)
                            .eveningCount(eveningCount)
                            .nightCount(nightCount)
                            .assignments(assignments)
                            .build()
            );
        }

        return ScheduleResponse.builder()
                .scheduleGroupId(scheduleGroup.getId())
                .scheduleYearMonth(scheduleGroup.getScheduleYearMonth())
                .days(days)
                .build();
    }

    private boolean isVisibleSchedule(Schedule schedule) {
        return schedule.getStatus() != ScheduleStatus.CANCELED
                && schedule.getStatus() != ScheduleStatus.SWAPPED;
    }

    private int countByShiftType(List<Schedule> schedules, ScheduleType scheduleType) {
        return (int) schedules.stream()
                .filter(schedule -> schedule.getShiftType() == scheduleType)
                .count();
    }

    private int getShiftOrder(ScheduleType shiftType) {
        return switch (shiftType) {
            case DAY -> 1;
            case EVENING -> 2;
            case NIGHT -> 3;
        };
    }

    private String getStartTime(ScheduleType shiftType) {
        return switch (shiftType) {
            case DAY -> "07:00";
            case EVENING -> "15:00";
            case NIGHT -> "23:00";
        };
    }

    private String getEndTime(ScheduleType shiftType) {
        return switch (shiftType) {
            case DAY -> "15:00";
            case EVENING -> "23:00";
            case NIGHT -> "07:00";
        };
    }

    private ScheduleType toScheduleType(String shiftName) {
        return switch (shiftName) {
            case "Day" -> ScheduleType.DAY;
            case "Evening" -> ScheduleType.EVENING;
            case "Night" -> ScheduleType.NIGHT;
            default -> throw new IllegalArgumentException("지원하지 않는 shiftName 입니다: " + shiftName);
        };
    }
}
