package com.scoinone.streamer.dto.response.streamer;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStreamerResponseDto {
    private String streamerId;
    private String virtualAssetId;
    private String liveStreamingPlatform;
    private String liveStreamingPlatformStreamerId;
    private String searchKeyword;
    private String youtubeId;
    private String naverCafeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
