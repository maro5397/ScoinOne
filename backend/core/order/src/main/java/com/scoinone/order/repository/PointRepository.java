package com.scoinone.order.repository;

import com.scoinone.order.entity.PointEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {
    Optional<PointEntity> findByUserId(String userId);

    Long deleteByUserId(String userId);
}
