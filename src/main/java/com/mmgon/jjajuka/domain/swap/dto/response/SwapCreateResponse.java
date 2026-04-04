package com.mmgon.jjajuka.domain.swap.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SwapCreateResponse {
    private Boolean success;
    private String message;

    public static SwapCreateResponse success() {
        return SwapCreateResponse.builder()
                .success(true)
                .message("교대 요청이 성공적으로 전송되었습니다. 디스코드 알림이 발송되었습니다.")
                .build();
    }
}
