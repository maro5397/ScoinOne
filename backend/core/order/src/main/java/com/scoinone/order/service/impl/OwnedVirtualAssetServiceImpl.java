package com.scoinone.order.service.impl;

import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import com.scoinone.order.repository.OwnedVirtualAssetRepository;
import com.scoinone.order.service.OwnedVirtualAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnedVirtualAssetServiceImpl implements OwnedVirtualAssetService {
    private final OwnedVirtualAssetRepository ownedVirtualAssetRepository;

    @Override
    public List<OwnedVirtualAssetEntity> getOwnedVirtualAssetsByUserId(String userId) {
        return ownedVirtualAssetRepository.findByUserId(userId);
    }

    @Override
    public String deleteAllOwnedVirtualAssets(String userId) {
        Long count = ownedVirtualAssetRepository.deleteByUserId(userId);
        return "All of OwnedVirtualAssets(" + count + ") deleted successfully";
    }
}
