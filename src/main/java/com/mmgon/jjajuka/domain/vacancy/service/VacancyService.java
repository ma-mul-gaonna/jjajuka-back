package com.mmgon.jjajuka.domain.vacancy.service;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.repository.ScheduleRepository;
import com.mmgon.jjajuka.domain.vacancy.dto.request.VacancyCreateRequest;
import com.mmgon.jjajuka.domain.vacancy.dto.response.VacancyDto;
import com.mmgon.jjajuka.domain.vacancy.dto.response.VacancyListResponse;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.event.VacancyCreatedEvent;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyErrorCode;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyException;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.repository.SwapRepository;
import com.mmgon.jjajuka.domain.vacancy.repository.VacancyRepository;
import com.mmgon.jjajuka.global.enums.VacancyStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final SwapRepository swapRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    public Vacancy findById(Integer id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vacancy not found: " + id));
    }

    @Transactional
    public VacancyDto createVacancy(Integer memberId, VacancyCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new VacancyException(VacancyErrorCode.MEMBER_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(request.scheduleId())
                .orElseThrow(() -> new VacancyException(VacancyErrorCode.SCHEDULE_NOT_FOUND));

        Vacancy vacancy = Vacancy.builder()
                .member(member)
                .schedule(schedule)
                .createdAt(LocalDate.now())
                .status(VacancyStatus.PENDING)
                .reason(request.reason())
                .build();

        Vacancy saved = vacancyRepository.save(vacancy);
        eventPublisher.publishEvent(new VacancyCreatedEvent(this, saved));
        return VacancyDto.from(saved);
    }

    public VacancyListResponse getMyVacancies(Integer memberId) {
        List<Vacancy> vacancies = vacancyRepository.findAllByMemberId(memberId);

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

    public VacancyListResponse getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAllWithMemberAndSchedule();

        List<VacancyDto> vacancyDtos = vacancies.stream()
                .map(vacancy -> {
                    Swap swap = swapRepository.findByRequesterScheduleId(vacancy.getSchedule().getId())
                            .orElse(null);
                    return VacancyDto.from(vacancy, swap);
                })
                .toList();

        return VacancyListResponse.builder()
                .success(true)
                .data(VacancyListResponse.DataWrapper.builder()
                        .vacancies(vacancyDtos)
                        .build())
                .build();
    }
}
