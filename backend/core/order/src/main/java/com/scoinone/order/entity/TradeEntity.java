package com.scoinone.order.entity;

import com.scoinone.order.entity.base.CreatableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trades")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder // 수정 및 삭제가 없으므로 Setter를 만들지 않음
public class TradeEntity extends CreatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buy_id")
    private BuyOrderEntity buyOrder;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    private SellOrderEntity sellOrder;

    private String virtualAssetId;

    private BigDecimal quantity;

    private BigDecimal price;
}
