package com.mmgon.jjajuka.domain.vacancy.controller;

import com.mmgon.jjajuka.domain.vacancy.controller.response.VacancyListResponse;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.service.VacancyService;
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
    public ResponseEntity<VacancyListResponse> getAll() {
        VacancyListResponse response = vacancyService.getAllVacancies();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(vacancyService.findById(id));
    }
}
