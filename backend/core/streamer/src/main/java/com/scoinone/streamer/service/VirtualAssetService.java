package com.scoinone.streamer.service;

import com.scoinone.streamer.entity.VirtualAssetEntity;
import java.util.List;

public interface VirtualAssetService {
    List<VirtualAssetEntity> getVirtualAssets();

    VirtualAssetEntity getVirtualAssetById(String id);

    VirtualAssetEntity updateVirtualAsset(String id, String name, String symbol, String description);
}
