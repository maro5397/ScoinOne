package com.scoinone.core.repository;

import com.scoinone.core.entity.OwnedVirtualAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnedVirtualAssetRepository extends JpaRepository<OwnedVirtualAsset, Long> {
    List<OwnedVirtualAsset> findByUser_Id(Long userId);
}