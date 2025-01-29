package com.scoinone.order.service;

import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import java.util.List;

public interface OwnedVirtualAssetService {
    List<OwnedVirtualAssetEntity> getOwnedVirtualAssetsByUserId(String userId);

    String deleteAllOwnedVirtualAssets(String userId);
}
