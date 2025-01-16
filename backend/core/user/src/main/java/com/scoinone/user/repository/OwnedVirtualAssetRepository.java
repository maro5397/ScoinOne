package com.scoinone.user.repository;

import com.scoinone.user.entity.OwnedVirtualAssetEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnedVirtualAssetRepository extends JpaRepository<OwnedVirtualAssetEntity, String> {
    List<OwnedVirtualAssetEntity> findByUser_Id(String userId);
    Optional<OwnedVirtualAssetEntity> findByUser_IdAndVirtualAssetId(String userId, String assetId);
}