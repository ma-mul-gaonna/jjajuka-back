package com.mmgon.jjajuka.global.enums;

public enum ScheduleStatus {
    ACTIVE,             // 현재 유효한 배정
    SWAP_PENDING,       // 교대 요청 진행 중
    VACANCY_PENDING,    // 결원 요청 진행 중
    CANCELED,           // 취소된 배정
    SWAPPED             // 교대로 인해 원래 배정이 변경됨
}

