package com.scoinone.core.service;

import com.scoinone.core.entity.VirtualAsset;

import java.util.List;

public interface VirtualAssetService {
    List<VirtualAsset> getVirtualAssets();

    VirtualAsset getVirtualAssetById(Long id);

    VirtualAsset createVirtualAsset(String name, String symbol, String description);

    VirtualAsset updateVirtualAsset(Long id, String name, String symbol, String description);

    String deleteVirtualAsset(Long id);
}
