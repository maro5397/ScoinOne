package com.scoinone.core.dto.response.virtualasset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetVirtualAssetResponseDto {
    private String virtualAssetId;
    private String name;
    private String symbol;
    private String description;
    private String createdAt;
}
