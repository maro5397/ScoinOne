package com.scoinone.streamer.service.impl;

import com.scoinone.streamer.entity.StreamerEntity;
import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.repository.StreamerRepository;
import com.scoinone.streamer.service.StreamerService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StreamerServiceImpl implements StreamerService {

    private final StreamerRepository streamerRepository;

    @Override
    public List<StreamerEntity> getStreamers() {
        return streamerRepository.findAll();
    }

    @Override
    public StreamerEntity getStreamerById(String id) {
        return streamerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Streamer not found with id: " + id));
    }

    @Override
    public StreamerEntity createStreamer(
            String liveStreamingPlatform,
            String liveStreamingPlatformStreamerId,
            String searchKeyword,
            String youtubeId,
            String naverCafeId,
            String virtualAssetName,
            String virtualAssetSymbol,
            String virtualAssetDescription
    ) {
        VirtualAssetEntity virtualAsset = VirtualAssetEntity.builder()
                .name(virtualAssetName)
                .symbol(virtualAssetSymbol)
                .description(virtualAssetDescription)
                .build();
        StreamerEntity streamer = StreamerEntity.builder()
                .virtualAsset(virtualAsset)
                .liveStreamingPlatform(liveStreamingPlatform)
                .liveStreamingPlatformStreamerId(liveStreamingPlatformStreamerId)
                .searchKeyword(searchKeyword)
                .youtubeId(youtubeId)
                .naverCafeId(naverCafeId)
                .build();
        return streamerRepository.save(streamer);
    }

    @Override
    public StreamerEntity updateStreamer(
            String id,
            String liveStreamingPlatform,
            String liveStreamingPlatformStreamerId,
            String searchKeyword,
            String youtubeId,
            String naverCafeId
    ) {
        StreamerEntity existedStreamer = getStreamerById(id);
        existedStreamer.setLiveStreamingPlatform(liveStreamingPlatform);
        existedStreamer.setLiveStreamingPlatformStreamerId(liveStreamingPlatformStreamerId);
        existedStreamer.setSearchKeyword(searchKeyword);
        existedStreamer.setYoutubeId(youtubeId);
        existedStreamer.setNaverCafeId(naverCafeId);
        return existedStreamer;
    }

    @Override
    public String deleteStreamer(String id) {
        streamerRepository.deleteById(id);
        return "Streamer/VirtualAsset deleted successfully";
    }
}
