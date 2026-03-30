package com.mmgon.jjajuka.domain.notification.controller.response;

import com.mmgon.jjajuka.domain.notification.dto.NotificationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NotificationListResponse {
    private Boolean success;
    private DataWrapper data;

    @Getter
    @Builder
    public static class DataWrapper {
        private List<NotificationDto> notifications;
        private Integer unreadCount;
    }
}
