package com.scoinone.core.repository;

import com.scoinone.core.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByBuyOrder_Buyer_Id(Long buyerId);
    List<Trade> findBySellOrder_Seller_Id(Long sellerId);
}