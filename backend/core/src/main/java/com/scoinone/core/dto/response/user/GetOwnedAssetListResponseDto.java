package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GetOwnedAssetListResponseDto {
    private List<GetOwnedAssetResponseDto> ownedAssets;
}