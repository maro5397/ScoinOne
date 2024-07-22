package com.scoinone.core.service.impl;

import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.VirtualAssetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VirtualAssetServiceImpl implements VirtualAssetService {

    private final VirtualAssetRepository virtualAssetRepository;

    @Override
    public List<VirtualAsset> getVirtualAssets() {
        return virtualAssetRepository.findAll();
    }

    @Override
    public VirtualAsset getVirtualAssetById(Long id) {
        return virtualAssetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VirtualAsset not found with id: " + id));
    }

    @Override
    public VirtualAsset createVirtualAsset(VirtualAsset virtualAsset) {
        return virtualAssetRepository.save(virtualAsset);
    }

    @Override
    public VirtualAsset updateVirtualAsset(Long id, VirtualAsset updatedVirtualAsset) {
        VirtualAsset existedVirtualAsset = getVirtualAssetById(id);
        existedVirtualAsset.setName(updatedVirtualAsset.getName());
        existedVirtualAsset.setDescription(updatedVirtualAsset.getDescription());
        existedVirtualAsset.setSymbol(updatedVirtualAsset.getSymbol());
        return virtualAssetRepository.save(existedVirtualAsset);
    }

    @Override
    public void deleteVirtualAsset(Long id) {
        virtualAssetRepository.deleteById(id);
    }
}
