package com.mmgon.jjajuka.domain.vacancy.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VacancyListResponse {
    private Boolean success;
    private DataWrapper data;

    @Getter
    @Builder
    public static class DataWrapper {
        private List<VacancyDto> vacancies;
    }
}
