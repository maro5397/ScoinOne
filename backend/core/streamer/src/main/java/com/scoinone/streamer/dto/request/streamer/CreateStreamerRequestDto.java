package com.scoinone.streamer.dto.request.streamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStreamerRequestDto {
    private String liveStreamingPlatform;
    private String liveStreamingPlatformStreamerId;
    private String searchKeyword;
    private String youtubeId;
    private String naverCafeId;
    private CreateVirtualAssetRequestDto virtualAsset;

    @Getter
    @Setter
    public static class CreateVirtualAssetRequestDto {
        private String name;
        private String symbol;
        private String description;
    }
}
