package com.mmgon.jjajuka.domain.schedule.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.schedule.dto.response.WorkScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {

    private static final String SESSION_MEMBER_KEY = "loginMember";

    private final ScheduleService scheduleService;

    @GetMapping("/{date}")
    public ResponseEntity<List<WorkScheduleResponse>> getMonthlySchedule(
            @PathVariable String date,
            HttpSession session) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            return ResponseEntity.status(401).build();
        }

        YearMonth yearMonth = YearMonth.parse(date);
        List<WorkScheduleResponse> response = scheduleService
                .findMonthlyScheduleByMember(loginMember.getId(), yearMonth)
                .stream()
                .map(WorkScheduleResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
