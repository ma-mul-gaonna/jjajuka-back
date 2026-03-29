package com.mmgon.jjajuka.domain.swap.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwapCreateRequest {
    @NotNull(message = "대상자 ID는 필수입니다.")
    private Integer targetMemberId;

    @NotNull(message = "스케줄 ID는 필수입니다.")
    private Integer scheduleId;
}
