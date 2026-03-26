package com.mmgon.dutyflow.domain.vacancy.service;

import com.mmgon.dutyflow.domain.vacancy.entity.Vacancy;
import com.mmgon.dutyflow.domain.vacancy.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    public Vacancy findById(Integer id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vacancy not found: " + id));
    }
}
