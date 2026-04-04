package com.mmgon.jjajuka.domain.vacancy.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.vacancy.dto.request.VacancyCreateRequest;
import com.mmgon.jjajuka.domain.vacancy.dto.response.VacancyDto;
import com.mmgon.jjajuka.domain.vacancy.dto.response.VacancyListResponse;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyErrorCode;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyException;
import com.mmgon.jjajuka.domain.vacancy.service.VacancyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacancy")
@RequiredArgsConstructor
public class VacancyController {

    private static final String SESSION_MEMBER_KEY = "loginMember";

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody VacancyCreateRequest request,
                                                     HttpSession session) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            throw new VacancyException(VacancyErrorCode.UNAUTHORIZED);
        }
        VacancyDto response = vacancyService.createVacancy(loginMember.getId(), request);
        return ResponseEntity.ok(response);
    }

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
