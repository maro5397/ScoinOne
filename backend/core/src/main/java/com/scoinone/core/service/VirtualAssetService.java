package com.scoinone.core.service;

import com.scoinone.core.entity.VirtualAsset;

import java.util.List;

public interface VirtualAssetService {
    List<VirtualAsset> getVirtualAssets();

    VirtualAsset getVirtualAssetById(Long id);

    VirtualAsset createVirtualAsset(VirtualAsset virtualAsset);

    VirtualAsset updateVirtualAsset(Long id, VirtualAsset updatedVirtualAsset);

    void deleteVirtualAsset(Long id);
}
