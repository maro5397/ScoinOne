package com.scoinone.core.entity;

import com.scoinone.core.common.NotificationStatus;
import com.scoinone.core.entity.base.CreatableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Notification extends CreatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiresAt;
}