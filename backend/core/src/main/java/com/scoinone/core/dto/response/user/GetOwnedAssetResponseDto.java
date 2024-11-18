package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetOwnedAssetResponseDto {
    private Long ownedVirtualAssetId;
    private Long userId;
    private Long virtualAssetId;
    private BigDecimal amount;
    private String updatedAt;
}
