package com.mmgon.dutyflow.domain.vacancy.repository;

import com.mmgon.dutyflow.domain.vacancy.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
