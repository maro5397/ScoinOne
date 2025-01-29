package com.scoinone.order.common.status;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),      // 거래 대기 중 (처리되지 않음)
    COMPLETED("COMPLETED"),    // 거래 완료 (성공적으로 거래됨)
    CANCELED("CANCELED"),;     // 거래 취소됨 (사용자 또는 시스템에 의해)

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
