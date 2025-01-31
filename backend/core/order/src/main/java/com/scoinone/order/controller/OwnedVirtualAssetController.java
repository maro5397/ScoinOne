package com.scoinone.order.controller;

import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.response.ownedvirtualasset.GetOwnedAssetResponseDto;
import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import com.scoinone.order.mapper.OwnedVirtualAssetMapper;
import com.scoinone.order.service.OwnedVirtualAssetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ownedasset")
@RequiredArgsConstructor
public class OwnedVirtualAssetController {
    private final OwnedVirtualAssetService ownedVirtualAssetService;

    @GetMapping
    public ResponseEntity<List<GetOwnedAssetResponseDto>> getOwnedVirtualAssets(
            @RequestHeader(value = "UserId") String userId
    ) {
        List<OwnedVirtualAssetEntity> ownedVirtualAssets =
                ownedVirtualAssetService.getOwnedVirtualAssetsByUserId(userId);
        return new ResponseEntity<>(
                OwnedVirtualAssetMapper.INSTANCE.ownedVirtualAssetsToGetOwnedAssetsResponseDto(ownedVirtualAssets),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> deleteAllCommentByUserId(@RequestParam("userId") String userId) {
        String result = ownedVirtualAssetService.deleteAllOwnedVirtualAssets(userId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
