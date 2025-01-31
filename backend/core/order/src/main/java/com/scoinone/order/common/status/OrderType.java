package com.scoinone.order.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    BUY_TYPE("BUY"),
    SELL_TYPE("SELL"),;

    private final String value;
}
