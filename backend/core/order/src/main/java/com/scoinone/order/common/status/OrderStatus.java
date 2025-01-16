package com.scoinone.order.common.status;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),      // 주문 대기 중 (처리되지 않음)
    COMPLETED("COMPLETED"),    // 주문 완료 (성공적으로 거래됨)
    CANCELED("CANCELED"),     // 주문 취소됨 (사용자 또는 시스템에 의해)
    FAILED("FAILED"),       // 주문 실패 (처리 중 오류 발생)
    FILLED("FILLED");       // 주문이 완전히 체결됨 (매도/매수 완료)

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
