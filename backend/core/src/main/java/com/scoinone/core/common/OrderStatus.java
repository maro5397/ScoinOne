package com.scoinone.core.common;

public enum OrderStatus {
    PENDING,      // 주문 대기 중 (처리되지 않음)
    COMPLETED,    // 주문 완료 (성공적으로 거래됨)
    CANCELED,     // 주문 취소됨 (사용자 또는 시스템에 의해)
    FAILED,       // 주문 실패 (처리 중 오류 발생)
    FILLED,       // 주문이 완전히 체결됨 (매도/매수 완료)
    PARTIALLY_FILLED, // 주문이 일부만 체결됨
    EXPIRED;      // 주문이 만료됨 (지정된 시간 내에 체결되지 않음)
}