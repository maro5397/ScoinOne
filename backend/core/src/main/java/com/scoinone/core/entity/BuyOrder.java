package com.scoinone.core.entity;

import com.scoinone.core.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "buy_orders")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class BuyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "virtual_asset_id")
    private VirtualAsset virtualAsset;

    private double quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tradeTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}