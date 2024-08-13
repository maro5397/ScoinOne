package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetOwnedAssetResponseDto {
    private String ownedVirtualAssetId;
    private String userId;
    private String virtualAssetId;
    private BigDecimal amount;
    private String updatedAt;
}
