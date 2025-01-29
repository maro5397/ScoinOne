package com.scoinone.order.repository;

import com.scoinone.order.entity.TradeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
    List<TradeEntity> findByVirtualAssetId(String virtualAssetId);
    List<TradeEntity> findByBuyOrder_BuyerIdOrSellOrder_SellerId(String buyerId, String sellerId);
    List<TradeEntity> findByVirtualAssetIdAndBuyOrder_BuyerIdOrVirtualAssetIdAndSellOrder_SellerId(
            String buyerVirtualAssetId, String buyerId,
            String sellerVirtualAssetId, String sellerId
    );
}
