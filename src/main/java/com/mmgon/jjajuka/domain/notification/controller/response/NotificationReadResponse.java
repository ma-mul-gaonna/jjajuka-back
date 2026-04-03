package com.mmgon.jjajuka.domain.notification.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationReadResponse {
    private Boolean success;
    private DataWrapper data;

    @Getter
    @Builder
    public static class DataWrapper {
        private Integer notificationId;
        private Boolean isRead;
    }
}
