package com.scoinone.core.repository;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.SellOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SellOrderRepository extends JpaRepository<SellOrder, Long> {
    Optional<List<SellOrder>> findBySeller_IdAndStatus(Long userId, OrderStatus status);

    @Query("SELECT s " +
            "FROM SellOrder s " +
            "WHERE s.price <= :buyPrice AND s.status = 'PENDING' " +
            "ORDER BY s.price DESC, s.createdAt ASC")
    Optional<List<SellOrder>> findMatchableSellOrders(@Param("buyPrice") BigDecimal buyPrice);
}