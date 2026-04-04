package com.mmgon.jjajuka.domain.dashboard.service;

import com.mmgon.jjajuka.domain.dashboard.dto.response.DashboardResponse;
import com.mmgon.jjajuka.domain.swap.repository.SwapRepository;
import com.mmgon.jjajuka.domain.vacancy.repository.VacancyRepository;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import com.mmgon.jjajuka.global.enums.VacancyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VacancyRepository vacancyRepository;
    private final SwapRepository swapRepository;

    public DashboardResponse getDashboard() {

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

        int requestCount =
                vacancyRepository.countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        startOfMonth, startOfNextMonth
                );

        int acceptCount =
                swapRepository.countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        SwapStatus.ACCEPTED,
                        startOfMonth,
                        startOfNextMonth
                );

        int rejectCount =
                swapRepository.countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        SwapStatus.REJECTED,
                        startOfMonth,
                        startOfNextMonth
                );

        return DashboardResponse.builder()
                .vacancyRequestCount(requestCount)
                .vacancyAcceptCount(acceptCount)
                .vacancyRejectCount(rejectCount)
                .build();
    }
}