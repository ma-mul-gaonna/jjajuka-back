package com.mmgon.jjajuka.domain.notification.service;

import com.mmgon.jjajuka.domain.notification.entity.Notification;
import com.mmgon.jjajuka.domain.notification.repository.NotificationRepository;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.global.enums.NotiType;
import com.mmgon.jjajuka.global.enums.ReferenceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
    }

    @Transactional
    public Notification createSwapNotification(Swap swap) {
        String requesterName = swap.getRequester().getName();
        String workDate = swap.getRequesterSchedule().getWorkDate().toString();

        Notification notification = Notification.builder()
                .receiver(swap.getTarget())
                .title("교대 근무 요청")
                .message(String.format("관리자님이 %s 근무에 대한 교대를 요청했습니다.", requesterName, workDate))
                .referenceType(ReferenceType.SWAP)
                .referenceId(swap.getId())
                .notiType(NotiType.SWAP_REQUEST)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }
}
