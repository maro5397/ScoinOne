package com.scoinone.streamer.dto.request.streamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStreamerRequestDto {
    private String liveStreamingPlatform;
    private String liveStreamingPlatformStreamerId;
    private String searchKeyword;
    private String youtubeId;
    private String naverCafeId;
}
