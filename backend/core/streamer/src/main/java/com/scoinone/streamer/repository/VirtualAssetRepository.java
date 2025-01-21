package com.scoinone.streamer.repository;

import com.scoinone.streamer.entity.VirtualAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualAssetRepository extends JpaRepository<VirtualAssetEntity, String> {
}