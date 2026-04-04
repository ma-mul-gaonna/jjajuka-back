package com.mmgon.jjajuka.domain.swap.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SwapErrorCode {
    UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    TARGET_MEMBER_NOT_FOUND("TARGET_MEMBER_NOT_FOUND", "대상 구성원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_SWAP_WITH_SELF("CANNOT_SWAP_WITH_SELF", "자기 자신과는 교대 요청을 할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SCHEDULE("INVALID_SCHEDULE", "교대 요청이 불가능한 스케줄입니다.", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND", "스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SCHEDULE_NOT_BELONG_TO_REQUESTER("SCHEDULE_NOT_BELONG_TO_REQUESTER", "해당 스케줄은 요청자의 것이 아닙니다.", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_CANCELLED("SCHEDULE_NOT_CANCELLED", "해당 스케줄은 결원 상태가 아닙니다.", HttpStatus.BAD_REQUEST),
    SCHEDULE_IN_PAST("SCHEDULE_IN_PAST", "과거 스케줄에 대한 교대 요청은 불가능합니다.", HttpStatus.BAD_REQUEST),

    SWAP_ALREADY_EXISTS("SWAP_ALREADY_EXISTS", "이미 진행 중인 교대 요청이 존재합니다.", HttpStatus.CONFLICT),
    SWAP_NOT_FOUND("SWAP_NOT_FOUND", "교대 요청을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SWAP_ALREADY_PROCESSED("SWAP_ALREADY_PROCESSED", "이미 응답이 완료된 교대 요청입니다.", HttpStatus.CONFLICT),
    TARGET_SCHEDULE_REQUIRED("TARGET_SCHEDULE_REQUIRED", "수락 시 대상 스케줄 ID는 필수입니다.", HttpStatus.BAD_REQUEST),
    TARGET_SCHEDULE_NOT_FOUND("TARGET_SCHEDULE_NOT_FOUND", "선택한 대상 스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TARGET_SCHEDULE_NOT_BELONG_TO_TARGET("TARGET_SCHEDULE_NOT_BELONG_TO_TARGET", "선택한 스케줄은 응답자의 스케줄이 아닙니다.", HttpStatus.BAD_REQUEST),
    INVALID_SWAP_STATUS("INVALID_SWAP_STATUS", "응답 상태는 ACCEPTED 또는 REJECTED 여야 합니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
