package com.mmgon.jjajuka.domain.rule.controller;


import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto;
import com.mmgon.jjajuka.domain.rule.dto.ScheduleRuleDto.Response;
import com.mmgon.jjajuka.domain.rule.service.ScheduleRuleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule-rules")
@RequiredArgsConstructor
public class ScheduleRuleController {

    private final ScheduleRuleService scheduleRuleService;

    @GetMapping
    public ResponseEntity<List<Response>> getAll() {
        return ResponseEntity.ok(scheduleRuleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleRuleDto.Response> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(scheduleRuleService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleRuleDto.Response> create(@RequestBody ScheduleRuleDto.Request request) {
        return ResponseEntity.ok(scheduleRuleService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleRuleDto.Response> update(@PathVariable Integer id,
                                                           @RequestBody ScheduleRuleDto.Request request) {
        return ResponseEntity.ok(scheduleRuleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        scheduleRuleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
