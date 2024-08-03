package com.scoinone.core.repository;

import com.scoinone.core.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<List<Trade>> findByBuyOrder_Buyer_UserId(Long buyerId);
    Optional<List<Trade>> findBySellOrder_Seller_UserId(Long sellerId);
}