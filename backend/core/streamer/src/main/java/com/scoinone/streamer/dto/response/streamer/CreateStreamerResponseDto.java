package com.scoinone.streamer.dto.response.streamer;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStreamerResponseDto {
    private String streamerId;
    private String liveStreamingPlatform;
    private String liveStreamingPlatformStreamerId;
    private String searchKeyword;
    private String youtubeId;
    private String naverCafeId;
    private CreateVirtualAssetResponseDto virtualAsset;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Getter
    @Setter
    public static class CreateVirtualAssetResponseDto {
        private String virtualAssetId;
        private String name;
        private String symbol;
        private String description;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }
}
