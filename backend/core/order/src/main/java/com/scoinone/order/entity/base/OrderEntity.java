package com.scoinone.order.entity.base;

import com.scoinone.order.common.status.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@SuperBuilder
public abstract class OrderEntity extends CreatableEntity {
    private String virtualAssetId;

    private BigDecimal quantity;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime tradeTime;
}
