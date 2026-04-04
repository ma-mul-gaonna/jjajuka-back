package com.mmgon.jjajuka.domain.swap.dto.response;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SwapDecisionResponse {

    private Integer swapId;
    private SwapStatus status;
    private Integer targetScheduleId;
    private String message;

    public static SwapDecisionResponse from(Swap swap) {
        return SwapDecisionResponse.builder()
                .swapId(swap.getId())
                .status(swap.getStatus())
                .targetScheduleId(
                        swap.getTargetSchedule() != null ? swap.getTargetSchedule().getId() : null
                )
                .message("교대 요청 응답이 완료되었습니다.")
                .build();
    }
}