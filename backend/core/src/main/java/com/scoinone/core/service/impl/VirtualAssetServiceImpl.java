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
    public VirtualAsset createVirtualAsset(String name, String symbol, String description) {
        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name(name)
                .symbol(symbol)
                .description(description)
                .build();
        return virtualAssetRepository.save(virtualAsset);
    }

    @Override
    public VirtualAsset updateVirtualAsset(Long id, String name, String symbol, String description) {
        VirtualAsset existedVirtualAsset = getVirtualAssetById(id);
        existedVirtualAsset.setName(name);
        existedVirtualAsset.setSymbol(symbol);
        existedVirtualAsset.setDescription(description);
        return existedVirtualAsset;
    }

    @Override
    public String deleteVirtualAsset(Long id) {
        virtualAssetRepository.deleteById(id);
        return "VirtualAsset deleted successfully";
    }
}
