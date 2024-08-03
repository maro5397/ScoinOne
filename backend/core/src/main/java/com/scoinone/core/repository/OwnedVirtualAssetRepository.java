package com.scoinone.core.repository;

import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.VirtualAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnedVirtualAssetRepository extends JpaRepository<OwnedVirtualAsset, Long> {
    Optional<List<OwnedVirtualAsset>> findByUser_UserId(Long userId);
}