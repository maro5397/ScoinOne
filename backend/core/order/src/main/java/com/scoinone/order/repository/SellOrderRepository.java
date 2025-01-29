package com.scoinone.order.repository;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.SellOrderEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellOrderRepository extends JpaRepository<SellOrderEntity, Long> {
    List<SellOrderEntity> findBySellerIdAndStatus(String userId, OrderStatus status);

    List<SellOrderEntity> findByVirtualAssetIdAndStatus(String userId, OrderStatus status);

    List<SellOrderEntity> findBySellerIdAndVirtualAssetIdAndStatus(
            String userId,
            String virtualAssetId,
            OrderStatus status
    );

    Optional<SellOrderEntity> findByIdAndSellerIdAndStatus(Long id, String userId, OrderStatus status);

    @Query("SELECT s " +
            "FROM SellOrderEntity s " +
            "WHERE s.price <= :buyPrice AND s.status = 'PENDING' " +
            "ORDER BY s.price DESC, s.createdAt ASC")
    List<SellOrderEntity> findMatchableSellOrders(@Param("buyPrice") BigDecimal buyPrice);
}
