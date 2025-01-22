package com.scoinone.streamer.service.impl;

import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.repository.VirtualAssetRepository;
import com.scoinone.streamer.service.VirtualAssetService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VirtualAssetServiceImpl implements VirtualAssetService {

    private final VirtualAssetRepository virtualAssetRepository;

    @Override
    public List<VirtualAssetEntity> getVirtualAssets() {
        return virtualAssetRepository.findAll();
    }

    @Override
    public VirtualAssetEntity getVirtualAssetById(String id) {
        return virtualAssetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VirtualAsset not found with id: " + id));
    }

    @Override
    public VirtualAssetEntity updateVirtualAsset(String id, String name, String symbol, String description) {
        VirtualAssetEntity existedVirtualAsset = getVirtualAssetById(id);
        existedVirtualAsset.setName(name);
        existedVirtualAsset.setSymbol(symbol);
        existedVirtualAsset.setDescription(description);
        return existedVirtualAsset;
    }
}
