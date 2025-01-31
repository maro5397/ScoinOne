package com.scoinone.streamer.controller;

import com.scoinone.streamer.dto.request.virtualasset.UpdateVirtualAssetRequestDto;
import com.scoinone.streamer.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.streamer.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.mapper.VirtualAssetMapper;
import com.scoinone.streamer.service.VirtualAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class VirtualAssetController {
    private final VirtualAssetService virtualAssetService;

    @GetMapping
    public ResponseEntity<List<GetVirtualAssetResponseDto>> getVirtualAssets() {
        List<VirtualAssetEntity> virtualAssets = virtualAssetService.getVirtualAssets();
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.virtualAssetsToGetVirtualAssetsResponseDto(virtualAssets),
                HttpStatus.OK
        );
    }

    @GetMapping("/{virtualAssetId}")
    public ResponseEntity<GetVirtualAssetResponseDto> getVirtualAsset(
            @PathVariable("virtualAssetId") String virtualAssetId
    ) {
        VirtualAssetEntity virtualAssetById = virtualAssetService.getVirtualAssetById(virtualAssetId);
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.virtualAssetToGetVirtualAssetResponseDto(virtualAssetById),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{virtualAssetId}")
    public ResponseEntity<UpdateVirtualAssetResponseDto> updateVirtualAsset(
            @PathVariable("virtualAssetId") String virtualAssetId,
            @RequestBody UpdateVirtualAssetRequestDto requestDto
    ) {
        VirtualAssetEntity updatedVirtualAsset = virtualAssetService.updateVirtualAsset(
                virtualAssetId,
                requestDto.getName(),
                requestDto.getSymbol(),
                requestDto.getDescription()
        );
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.virtualAssetToUpdateVirtualAssetResponseDto(updatedVirtualAsset),
                HttpStatus.OK
        );
    }
}
