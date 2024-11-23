package com.scoinone.core.repository;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    Optional<List<BuyOrder>> findByBuyer_IdAndStatus(Long userId, OrderStatus status);

    @Query("SELECT b " +
            "FROM BuyOrder b " +
            "WHERE b.price >= :sellPrice AND b.status = 'PENDING' " +
            "ORDER BY b.price ASC, b.createdAt ASC")
    Optional<List<BuyOrder>> findMatchableBuyOrders(@Param("sellPrice") BigDecimal sellPrice);
}