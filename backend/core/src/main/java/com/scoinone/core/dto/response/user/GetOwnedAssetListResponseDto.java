package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOwnedAssetListResponseDto {
    private List<OwnedAssetDto> ownedAssets;

    @Getter
    @Setter
    public static class OwnedAssetDto {
        private String ownedVirtualAssetId;
        private String userId;
        private String virtualAssetId;
        private Double amount;
        private String updatedAt;
    }
}