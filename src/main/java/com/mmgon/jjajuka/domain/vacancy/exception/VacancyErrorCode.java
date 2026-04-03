package com.mmgon.jjajuka.domain.vacancy.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VacancyErrorCode {
    UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "관리자 권한이 필요합니다.", HttpStatus.FORBIDDEN),
    VACANCY_NOT_FOUND("VACANCY_NOT_FOUND", "결원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
