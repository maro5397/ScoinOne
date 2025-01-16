package com.scoinone.user.dto.response.user;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOwnedAssetsResponseDto {
    private List<GetOwnedAssetResponseDto> ownedAssets;
}