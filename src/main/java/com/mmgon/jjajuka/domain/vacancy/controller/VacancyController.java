package com.mmgon.dutyflow.domain.vacancy.controller;

import com.mmgon.dutyflow.domain.vacancy.entity.Vacancy;
import com.mmgon.dutyflow.domain.vacancy.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAll() {
        return ResponseEntity.ok(vacancyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(vacancyService.findById(id));
    }
}
