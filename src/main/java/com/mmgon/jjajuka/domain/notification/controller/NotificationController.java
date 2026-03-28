package com.mmgon.jjajuka.domain.notification.controller;

import com.mmgon.jjajuka.domain.notification.entity.Notification;
import com.mmgon.jjajuka.domain.notification.service.NotificationService;
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
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.findById(id));
    }
}
