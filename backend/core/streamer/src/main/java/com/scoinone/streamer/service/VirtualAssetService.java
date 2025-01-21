package com.scoinone.streamer.service;

import com.scoinone.streamer.entity.VirtualAssetEntity;
import java.util.List;

public interface VirtualAssetService {
    List<VirtualAssetEntity> getVirtualAssets();

    VirtualAssetEntity getVirtualAssetById(String id);

    VirtualAssetEntity createVirtualAsset(String name, String symbol, String description);

    VirtualAssetEntity updateVirtualAsset(String id, String name, String symbol, String description);

    String deleteVirtualAsset(String id);
}
