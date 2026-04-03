package com.mmgon.jjajuka.domain.vacancy.controller.response;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class VacancyNotificationResponse {
    private String title;
    private String message;
    private VacancyDto vacancy;

    public static VacancyNotificationResponse from(Vacancy vacancy) {
        String title = "새로운 결원 요청";
        String message = String.format("%s님이 %s에 결원 요청을 하여 근무 결원이 등록되었습니다.",
                vacancy.getMember().getName(),
                vacancy.getSchedule().getWorkDate().format(DateTimeFormatter.ofPattern("M월 d일")));

        return VacancyNotificationResponse.builder()
                .title(title)
                .message(message)
                .vacancy(VacancyDto.from(vacancy))
                .build();
    }
}
