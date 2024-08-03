package com.scoinone.core.repository;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    Optional<List<BuyOrder>> findByBuyer_UserIdAndStatus(Long userId, OrderStatus status);
}