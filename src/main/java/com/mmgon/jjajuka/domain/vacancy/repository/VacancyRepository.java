package com.mmgon.jjajuka.domain.vacancy.repository;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.global.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    int countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(LocalDate start, LocalDate end);

    boolean existsBySchedule_ScheduleGroup_Id(Integer scheduleGroupId);

    @Query("SELECT v FROM Vacancy v JOIN FETCH v.member JOIN FETCH v.schedule WHERE v.status = :status")
    List<Vacancy> findAllByStatus(VacancyStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Vacancy v WHERE v.schedule.scheduleGroup.id = :scheduleGroupId")
    void deleteAllByScheduleGroupId(@Param("scheduleGroupId") Integer scheduleGroupId);
}
