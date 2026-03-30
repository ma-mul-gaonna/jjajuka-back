package com.mmgon.jjajuka.domain.notification.entity;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.NotiType;
import com.mmgon.jjajuka.global.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "noti_type", nullable = false)
    private NotiType notiType;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public void markAsRead() {
        this.isRead = true;
    }
}
