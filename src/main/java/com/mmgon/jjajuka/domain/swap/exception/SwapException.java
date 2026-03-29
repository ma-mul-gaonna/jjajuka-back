package com.mmgon.jjajuka.domain.swap.exception;

import lombok.Getter;

@Getter
public class SwapException extends RuntimeException {
    private final SwapErrorCode errorCode;

    public SwapException(SwapErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
