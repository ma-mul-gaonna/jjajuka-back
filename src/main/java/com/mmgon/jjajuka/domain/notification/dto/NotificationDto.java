package com.mmgon.jjajuka.domain.notification.dto;

import com.mmgon.jjajuka.domain.notification.entity.Notification;
import com.mmgon.jjajuka.global.enums.NotiType;
import com.mmgon.jjajuka.global.enums.ReferenceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDto {
    private Integer notificationId;
    private String title;
    private String message;
    private NotiType notiType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Integer relatedId;
    private ReferenceType relatedType;

    public static NotificationDto from(Notification notification) {
        return NotificationDto.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .notiType(notification.getNotiType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .relatedId(notification.getReferenceId())
                .relatedType(notification.getReferenceType())
                .build();
    }
}