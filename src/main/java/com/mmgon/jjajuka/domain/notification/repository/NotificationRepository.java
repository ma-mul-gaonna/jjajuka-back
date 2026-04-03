package com.mmgon.jjajuka.domain.notification.repository;

import com.mmgon.jjajuka.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Integer receiverId);
}
