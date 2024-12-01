package com.scoinone.core.controller;

import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.virtualasset.CreateVirtualAssetRequestDto;
import com.scoinone.core.dto.request.virtualasset.UpdateVirtualAssetRequestDto;
import com.scoinone.core.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetsResponseDto;
import com.scoinone.core.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.mapper.VirtualAssetMapper;
import com.scoinone.core.service.VirtualAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateVirtualAssetResponseDto> createVirtualAsset(
            @RequestBody CreateVirtualAssetRequestDto requestDto
    ) {
        VirtualAsset virtualAsset = virtualAssetService.createVirtualAsset(
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
        List<VirtualAsset> virtualAssets = virtualAssetService.getVirtualAssets();
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.listToGetVirtualAssetListResponseDto(virtualAssets),
                HttpStatus.OK
        );
    }

    @GetMapping("/{virtualAssetId}")
    public ResponseEntity<GetVirtualAssetResponseDto> getVirtualAsset(
            @PathVariable("virtualAssetId") Long virtualAssetId
    ) {
        VirtualAsset virtualAssetById = virtualAssetService.getVirtualAssetById(virtualAssetId);
        return new ResponseEntity<>(
                VirtualAssetMapper.INSTANCE.virtualAssetToGetVirtualAssetResponseDto(virtualAssetById),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{virtualAssetId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UpdateVirtualAssetResponseDto> updateVirtualAsset(
            @PathVariable("virtualAssetId") Long virtualAssetId,
            @RequestBody UpdateVirtualAssetRequestDto requestDto
    ) {
        VirtualAsset updatedVirtualAsset = virtualAssetService.updateVirtualAsset(
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteVirtualAsset(
            @PathVariable("virtualAssetId") Long virtualAssetId
    ) {
        String result = virtualAssetService.deleteVirtualAsset(virtualAssetId);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
