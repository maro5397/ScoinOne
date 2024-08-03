package com.scoinone.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "trades")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder // 수정 및 삭제가 없으므로 Setter를 만들지 않음
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @ManyToOne
    @JoinColumn(name = "buy_id")
    private BuyOrder buyOrder;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    private SellOrder sellOrder;

    @ManyToOne
    @JoinColumn(name = "virtual_asset_id")
    private VirtualAsset virtualAsset;

    private double quantity;
    private double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tradeTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}