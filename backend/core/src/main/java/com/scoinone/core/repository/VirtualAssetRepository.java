package com.scoinone.core.repository;

import com.scoinone.core.entity.VirtualAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualAssetRepository extends JpaRepository<VirtualAsset, Long> {
}