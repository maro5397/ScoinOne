package com.scoinone.core.entity;

import com.scoinone.core.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "sell_orders")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class SellOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "virtual_asset_id")
    private VirtualAsset virtualAsset;

    private double quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Enum 타입으로 정의 필요

    @Temporal(TemporalType.TIMESTAMP)
    private Date tradeTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Getters and Setters
}