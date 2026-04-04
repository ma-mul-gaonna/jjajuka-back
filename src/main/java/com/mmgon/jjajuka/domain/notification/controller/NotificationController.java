package com.mmgon.jjajuka.domain.notification.controller;

import com.mmgon.jjajuka.domain.notification.controller.response.NotificationListResponse;
import com.mmgon.jjajuka.domain.notification.controller.response.NotificationReadResponse;
import com.mmgon.jjajuka.domain.notification.entity.Notification;
import com.mmgon.jjajuka.domain.notification.service.NotificationService;
import com.mmgon.jjajuka.domain.swap.service.DiscordNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<NotificationListResponse> getNotificationsByReceiverId(
            @RequestParam Integer receiverId
    ) {
        NotificationListResponse response = notificationService.getNotificationsByReceiverId(receiverId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<NotificationReadResponse> markAsRead(
            @PathVariable Integer notificationId
    ) {
        Notification notification = notificationService.markAsRead(notificationId);

        NotificationReadResponse response = NotificationReadResponse.builder()
                .success(true)
                .data(NotificationReadResponse.DataWrapper.builder()
                        .notificationId(notification.getId())
                        .isRead(notification.getIsRead())
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.findById(id));
    }
}
