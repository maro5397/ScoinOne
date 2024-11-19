package com.scoinone.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder // 수정 및 삭제가 없으므로 Setter를 만들지 않음
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buy_id")
    private BuyOrder buyOrder;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    private SellOrder sellOrder;

    @ManyToOne
    @JoinColumn(name = "virtual_asset_id")
    private VirtualAsset virtualAsset;

    private BigDecimal quantity;
    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}