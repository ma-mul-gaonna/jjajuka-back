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

    public static SwapDecisionResponse from(Swap swap) {
        String message = switch (swap.getStatus()) {
            case ACCEPTED -> "교대 요청을 수락했습니다.";
            case REJECTED -> "교대 요청을 거절했습니다.";
            default -> "교대 요청 응답이 완료되었습니다.";
        };

        return SwapDecisionResponse.builder()
                .success(true)
                .message(message)
                .data(Data.builder()
                        .swapRequestId(swap.getId())
                        .status(swap.getStatus())
                        .build())
                .build();
    }
}
