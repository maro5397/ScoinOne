package com.scoinone.core.dto.response.virtualasset;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetVirtualAssetListResponseDto {
    private List<GetVirtualAssetResponseDto> virtualAssets;
}