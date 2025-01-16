package com.scoinone.user.common.status;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    READ("READ"),        // 읽음
    UNREAD("UNREAD");      // 읽지 않음

    private final String value;

    NotificationStatus(String value) {
        this.value = value;
    }
}
