package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOwnedAssetResponseDto {
    private String ownedVirtualAssetId;
    private String userId;
    private String virtualAssetId;
    private Double amount;
    private String updatedAt;
}
