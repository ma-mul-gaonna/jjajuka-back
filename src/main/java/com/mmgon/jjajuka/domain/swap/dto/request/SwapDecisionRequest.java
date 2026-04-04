package com.mmgon.jjajuka.domain.swap.dto.request;

import com.mmgon.jjajuka.global.enums.SwapStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SwapDecisionRequest {

    @NotNull
    private SwapStatus swapStatus;
    private Integer targetScheduleId;
}
