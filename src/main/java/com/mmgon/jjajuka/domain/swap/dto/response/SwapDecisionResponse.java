package com.mmgon.jjajuka.domain.swap.dto.response;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SwapDecisionResponse {

    private Boolean success;
    private String message;
    private Data data;

    @Getter
    @Builder
    public static class Data {
        private Integer swapRequestId;
        private SwapStatus status;
    }

    public static SwapDecisionResponse accepted(Swap swap) {
        return SwapDecisionResponse.builder()
                .success(true)
                .message("교대 요청을 수락했습니다.")
                .data(Data.builder()
                        .swapRequestId(swap.getId())
                        .status(swap.getStatus())
                        .build())
                .build();
    }

    public static SwapDecisionResponse rejected(Swap swap) {
        return SwapDecisionResponse.builder()
                .success(true)
                .message("교대 요청을 거절했습니다.")
                .data(Data.builder()
                        .swapRequestId(swap.getId())
                        .status(swap.getStatus())
                        .build())
                .build();
    }
}
