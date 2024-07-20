package com.scoinone.core.dto.response.virtualasset;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetVirtualAssetListResponseDto {
    private List<VirtualAssetDto> virtualAssets;

    @Getter
    @Setter
    public static class VirtualAssetDto {
        private String virtualAssetId;
        private String name;
        private String symbol;
        private String description;
        private String createdAt;
    }
}