package com.scoinone.virtualasset.repository;

import com.scoinone.virtualasset.entity.VirtualAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualAssetRepository extends JpaRepository<VirtualAssetEntity, String> {
}