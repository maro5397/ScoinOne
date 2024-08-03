package com.scoinone.core.repository;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.SellOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellOrderRepository extends JpaRepository<SellOrder, Long> {
    Optional<List<SellOrder>> findBySeller_UserIdAndStatus(Long userId, OrderStatus status);
}