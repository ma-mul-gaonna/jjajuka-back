package com.mmgon.jjajuka.domain.vacancy.dto.response;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.global.enums.VacancyStatus;
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
        String title = "";
        String message = "";

        if(vacancy.getStatus() == VacancyStatus.APPROVED){
            title = "교대 요청 수락";
            message = String.format("%s님의 %s근무 교대 요청이 수락되었습니다.",
                    vacancy.getMember().getName(),
                    vacancy.getSchedule().getWorkDate().format(DateTimeFormatter.ofPattern("M월 d일")));
        }else if(vacancy.getStatus() == VacancyStatus.PENDING) {
            title = "새로운 결원 요청";
            message = String.format("%s님이 %s에 결원 요청을 하여 근무 결원이 등록되었습니다.",
                    vacancy.getMember().getName(),
                    vacancy.getSchedule().getWorkDate().format(DateTimeFormatter.ofPattern("M월 d일")));
        }else if(vacancy.getStatus() == VacancyStatus.REJECTED){
            title = "교대 요청 거절";
            message = String.format("%s님의 %s근무 교대 요청이 거절되었습니다.",
                    vacancy.getMember().getName(),
                    vacancy.getSchedule().getWorkDate().format(DateTimeFormatter.ofPattern("M월 d일")));
        }
        return VacancyNotificationResponse.builder()
                .title(title)
                .message(message)
                .vacancy(VacancyDto.from(vacancy))
                .build();
    }
}
