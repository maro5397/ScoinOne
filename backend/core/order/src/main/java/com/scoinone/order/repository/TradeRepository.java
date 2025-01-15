package com.scoinone.order.repository;

import com.scoinone.order.entity.TradeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
    List<TradeEntity> findByBuyOrder_BuyerId(String buyerId);
    List<TradeEntity> findBySellOrder_SellerId(String sellerId);
}
