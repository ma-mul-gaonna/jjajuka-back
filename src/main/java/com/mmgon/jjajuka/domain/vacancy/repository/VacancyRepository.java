package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {

    @Query("SELECT v FROM Vacancy v " +
            "JOIN FETCH v.member " +
            "JOIN FETCH v.schedule " +
            "ORDER BY v.createdAt DESC")
    List<Vacancy> findAllWithMemberAndSchedule();

    @Query("SELECT v FROM Vacancy v " +
            "JOIN FETCH v.member " +
            "JOIN FETCH v.schedule " +
            "WHERE v.member.id = :memberId " +
            "ORDER BY v.createdAt DESC")
    List<Vacancy> findAllByMemberId(Integer memberId);
}
