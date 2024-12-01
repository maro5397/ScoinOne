package com.scoinone.core.repository;

import com.scoinone.core.common.status.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    List<BuyOrder> findByBuyer_IdAndStatus(Long userId, OrderStatus status);

    @Query("SELECT b " +
            "FROM BuyOrder b " +
            "WHERE b.price >= :sellPrice AND b.status = 'PENDING' " +
            "ORDER BY b.price ASC, b.createdAt ASC")
    List<BuyOrder> findMatchableBuyOrders(@Param("sellPrice") BigDecimal sellPrice);

    Long deleteByIdAndBuyer_Id(Long id, Long buyerId);
}