package com.mmgon.jjajuka.domain.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {
    private int vacancyRequestCount;
    private int vacancyAcceptCount;
    private int vacancyRejectCount;
}