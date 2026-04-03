package com.mmgon.jjajuka.domain.vacancy.exception;

import lombok.Getter;

@Getter
public class VacancyException extends RuntimeException {
    private final VacancyErrorCode errorCode;

    public VacancyException(VacancyErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
