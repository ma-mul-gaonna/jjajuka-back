package com.mmgon.jjajuka.domain.rule.controller;

import com.mmgon.jjajuka.domain.rule.entity.ScheduleRule;
import com.mmgon.jjajuka.domain.rule.service.ScheduleRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-rules")
@RequiredArgsConstructor
public class ScheduleRuleController {

    private final ScheduleRuleService scheduleRuleService;

    @GetMapping
    public ResponseEntity<List<ScheduleRule>> getAll() {
        return ResponseEntity.ok(scheduleRuleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleRule> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(scheduleRuleService.findById(id));
    }
}
