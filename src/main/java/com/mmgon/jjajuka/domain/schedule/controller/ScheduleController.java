package com.mmgon.jjajuka.domain.schedule.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.schedule.controller.request.ScheduleGenerateRequest;
import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleGenerateResponse;
import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.service.ScheduleGenerationFacade;
import com.mmgon.jjajuka.domain.schedule.service.ScheduleService;
import com.mmgon.jjajuka.domain.swap.exception.SwapErrorCode;
import com.mmgon.jjajuka.domain.swap.exception.SwapException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleGenerationFacade scheduleGenerationFacade;

    private static final String SESSION_MEMBER_KEY = "loginMember";

    @GetMapping
    public ResponseEntity<List<Schedule>> getAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @GetMapping("/work-schedules/{scheduleGroupId}")
    public ResponseEntity<ScheduleResponse> getWorkSchedules(
            @PathVariable Integer scheduleGroupId,
            HttpSession session
    ) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            throw new SwapException(SwapErrorCode.UNAUTHORIZED);
        }

        return ResponseEntity.ok(scheduleService.getSchedules(scheduleGroupId));
    }

    // 근무표 규칙 저장 + 근무표 생성 오케스트레이션 API
    @PostMapping("/work-schedules/generate-with-rules")
    public ResponseEntity<ScheduleGenerateResponse> generateWithRules(
            @RequestBody ScheduleGenerateRequest request,
            HttpSession session
    ) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            throw new SwapException(SwapErrorCode.UNAUTHORIZED);
        }

        return ResponseEntity.ok(scheduleGenerationFacade.generateWithRules(request));
    }

}
