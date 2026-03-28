package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
