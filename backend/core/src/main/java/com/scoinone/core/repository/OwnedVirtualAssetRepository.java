package com.scoinone.core.repository;

import com.scoinone.core.entity.OwnedVirtualAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnedVirtualAssetRepository extends JpaRepository<OwnedVirtualAsset, Long> {
}