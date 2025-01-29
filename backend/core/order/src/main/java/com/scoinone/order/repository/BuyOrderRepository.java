package com.scoinone.order.repository;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.BuyOrderEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrderEntity, Long> {
    List<BuyOrderEntity> findByBuyerIdAndStatus(String userId, OrderStatus status);

    List<BuyOrderEntity> findByVirtualAssetIdAndStatus(String userId, OrderStatus status);

    List<BuyOrderEntity> findByBuyerIdAndVirtualAssetIdAndStatus(
            String userId,
            String virtualAssetId,
            OrderStatus status
    );

    Optional<BuyOrderEntity> findByIdAndBuyerIdAndStatus(Long id, String userId, OrderStatus status);

    @Query("SELECT b " +
            "FROM BuyOrderEntity b " +
            "WHERE b.price >= :sellPrice AND b.status = 'PENDING' " +
            "ORDER BY b.price ASC, b.createdAt ASC")
    List<BuyOrderEntity> findMatchableBuyOrders(@Param("sellPrice") BigDecimal sellPrice);
}
