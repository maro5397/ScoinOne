package com.scoinone.order.repository;

import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnedVirtualAssetRepository extends JpaRepository<OwnedVirtualAssetEntity, String> {
    List<OwnedVirtualAssetEntity> findByUserId(String userId);
    Optional<OwnedVirtualAssetEntity> findByUserIdAndVirtualAssetId(String userId, String assetId);
    Long deleteByUserId(String userId);
}
