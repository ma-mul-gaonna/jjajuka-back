package com.mmgon.jjajuka.domain.vacancy.service;

import com.mmgon.jjajuka.domain.vacancy.controller.response.VacancyDto;
import com.mmgon.jjajuka.domain.vacancy.controller.response.VacancyListResponse;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.event.VacancyCreatedEvent;
import com.mmgon.jjajuka.domain.vacancy.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    public Vacancy findById(Integer id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vacancy not found: " + id));
    }

    public VacancyListResponse getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAllWithMemberAndSchedule();

        List<VacancyDto> vacancyDtos = vacancies.stream()
                .map(VacancyDto::from)
                .toList();

        return VacancyListResponse.builder()
                .success(true)
                .data(VacancyListResponse.DataWrapper.builder()
                        .vacancies(vacancyDtos)
                        .build())
                .build();
    }
}
