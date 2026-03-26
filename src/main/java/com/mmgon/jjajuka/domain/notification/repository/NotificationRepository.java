package com.mmgon.dutyflow.domain.notification.repository;

import com.mmgon.dutyflow.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
