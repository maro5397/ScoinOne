package com.scoinone.order.common.status;

import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntervalType {
    ONE_MINUTE(1, ChronoUnit.MINUTES, "1M"),
    FIVE_MINUTES(5, ChronoUnit.MINUTES, "5M"),
    FIFTEEN_MINUTES(15, ChronoUnit.MINUTES, "15M"),
    ONE_HOUR(1, ChronoUnit.HOURS, "1H"),
    FOUR_HOURS(4, ChronoUnit.HOURS, "4H"),
    ONE_DAY(1, ChronoUnit.DAYS, "1D");

    private final Integer amount;
    private final ChronoUnit unit;
    private final String value;

    public static IntervalType from(String source) {
        for (IntervalType type : IntervalType.values()) {
            if (type.value.equals(source)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid IntervalType value: " + source);
    }
}
