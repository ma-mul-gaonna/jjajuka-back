package com.mmgon.dutyflow.domain.dayoff.controller;

import com.mmgon.dutyflow.domain.dayoff.entity.Dayoff;
import com.mmgon.dutyflow.domain.dayoff.service.DayoffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dayoffs")
@RequiredArgsConstructor
public class DayoffController {

    private final DayoffService dayoffService;

    @GetMapping
    public ResponseEntity<List<Dayoff>> getAll() {
        return ResponseEntity.ok(dayoffService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dayoff> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(dayoffService.findById(id));
    }
}
