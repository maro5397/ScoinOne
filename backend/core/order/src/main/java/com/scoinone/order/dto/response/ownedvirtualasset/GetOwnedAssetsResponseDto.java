package com.scoinone.order.dto.response.ownedvirtualasset;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOwnedAssetsResponseDto {
    private List<GetOwnedAssetResponseDto> ownedAssets;
}
