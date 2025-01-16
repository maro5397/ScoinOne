package com.scoinone.virtualasset.controller;

import com.scoinone.virtualasset.dto.common.DeleteResponseDto;
import com.scoinone.virtualasset.dto.request.virtualasset.CreateVirtualAssetRequestDto;
import com.scoinone.virtualasset.dto.request.virtualasset.UpdateVirtualAssetRequestDto;
import com.scoinone.virtualasset.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.GetVirtualAssetsResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.virtualasset.entity.VirtualAssetEntity;
import com.scoinone.virtualasset.mapper.VirtualAssetMapper;
import com.scoinone.virtualasset.service.VirtualAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class VirtualAssetController {
    private final VirtualAssetService virtualAssetService;

    @PostMapping
    public ResponseEntity<CreateVirtualAssetResponseDto> createVirtualAsset(
            @RequestBody CreateVirtualAssetRequestDto requestDto
    ) {
        VirtualAssetEntity virtualAsset = virtualAssetService.createVirtualAsset(
                requestDto.getName(),
                requestDto.getSymbol(),
                requestDto.getDescription());
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.virtualAssetToCreateVirtualAssetResponseDto(virtualAsset),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetVirtualAssetsResponseDto> getVirtualAssets() {
        List<VirtualAssetEntity> virtualAssets = virtualAssetService.getVirtualAssets();
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.listToGetVirtualAssetsResponseDto(virtualAssets),
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

    @DeleteMapping("/{virtualAssetId}")
    public ResponseEntity<DeleteResponseDto> deleteVirtualAsset(
            @PathVariable("virtualAssetId") String virtualAssetId
    ) {
        String result = virtualAssetService.deleteVirtualAsset(virtualAssetId);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
