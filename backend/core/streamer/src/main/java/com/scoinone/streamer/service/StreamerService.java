package com.scoinone.streamer.service;

import com.scoinone.streamer.entity.StreamerEntity;
import java.util.List;

public interface StreamerService {
    List<StreamerEntity> getStreamers();

    StreamerEntity getStreamerById(String id);

    StreamerEntity createStreamer(
            String liveStreamingPlatform,
            String liveStreamingPlatformStreamerId,
            String searchKeyword,
            String youtubeId,
            String naverCafeId,
            String virtualAssetName,
            String virtualAssetSymbol,
            String virtualAssetDescription
    );

    StreamerEntity updateStreamer(
            String id,
            String liveStreamingPlatform,
            String liveStreamingPlatformStreamerId,
            String searchKeyword,
            String youtubeId,
            String naverCafeId
    );

    String deleteStreamer(String id);
}
