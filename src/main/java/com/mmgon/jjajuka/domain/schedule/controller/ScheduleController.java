package com.mmgon.jjajuka.domain.schedule.controller;

import com.mmgon.jjajuka.domain.schedule.controller.response.ScheduleResponse;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

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
            @PathVariable Integer scheduleGroupId
    ) {
        return ResponseEntity.ok(scheduleService.getSchedules(scheduleGroupId));
    }


//    @PostMapping("/work-schedules/generate")


    // 근무표 규칙 저장 + 근무표 생성 오케스트레이션 API
//    @PostMapping("/work-schedules/generate-with-rules")

}
