package com.scoinone.streamer.dto.request.virtualasset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVirtualAssetRequestDto {
    private String name;
    private String symbol;
    private String description;
}