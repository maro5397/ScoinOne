package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOwnedAssetsResponseDto {
    private List<GetOwnedAssetResponseDto> ownedAssets;
}