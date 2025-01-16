package com.scoinone.virtualasset.dto.response.virtualasset;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetVirtualAssetsResponseDto {
    private List<GetVirtualAssetResponseDto> virtualAssets;
}