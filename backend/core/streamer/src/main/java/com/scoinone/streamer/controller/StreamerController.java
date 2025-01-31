package com.scoinone.streamer.controller;

import com.scoinone.streamer.dto.common.DeleteResponseDto;
import com.scoinone.streamer.dto.request.streamer.CreateStreamerRequestDto;
import com.scoinone.streamer.dto.request.streamer.UpdateStreamerRequestDto;
import com.scoinone.streamer.dto.response.streamer.CreateStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.GetStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.UpdateStreamerResponseDto;
import com.scoinone.streamer.entity.StreamerEntity;
import com.scoinone.streamer.mapper.StreamerMapper;
import com.scoinone.streamer.service.StreamerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/streamer")
@RequiredArgsConstructor
public class StreamerController {
    private final StreamerService streamerService;

    @PostMapping
    public ResponseEntity<CreateStreamerResponseDto> createStreamer(
            @RequestBody CreateStreamerRequestDto requestDto
    ) {
        StreamerEntity streamer = streamerService.createStreamer(
                requestDto.getLiveStreamingPlatform(),
                requestDto.getLiveStreamingPlatformStreamerId(),
                requestDto.getSearchKeyword(),
                requestDto.getYoutubeId(),
                requestDto.getNaverCafeId(),
                requestDto.getVirtualAsset().getName(),
                requestDto.getVirtualAsset().getSymbol(),
                requestDto.getVirtualAsset().getDescription()
        );
        return new ResponseEntity<>(
                StreamerMapper.INSTANCE.streamerToCreateStreamerResponseDto(streamer),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<GetStreamerResponseDto>> getStreamers() {
        List<StreamerEntity> streamers = streamerService.getStreamers();
        return new ResponseEntity<>(
                StreamerMapper.INSTANCE.streamersToGetStreamersResponseDto(streamers),
                HttpStatus.OK
        );
    }

    @GetMapping("/{streamerId}")
    public ResponseEntity<GetStreamerResponseDto> getStreamer(@PathVariable("streamerId") String streamerId) {
        StreamerEntity streamerById = streamerService.getStreamerById(streamerId);
        return new ResponseEntity<>(
                StreamerMapper.INSTANCE.streamerToGetStreamerResponseDto(streamerById),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{streamerId}")
    public ResponseEntity<UpdateStreamerResponseDto> updateStreamer(
            @PathVariable("streamerId") String streamerId,
            @RequestBody UpdateStreamerRequestDto requestDto
    ) {
        StreamerEntity updatedStreamer = streamerService.updateStreamer(
                streamerId,
                requestDto.getLiveStreamingPlatform(),
                requestDto.getLiveStreamingPlatformStreamerId(),
                requestDto.getSearchKeyword(),
                requestDto.getYoutubeId(),
                requestDto.getNaverCafeId()
        );
        return new ResponseEntity<>(
                StreamerMapper.INSTANCE.streamerToUpdateStreamerResponseDto(updatedStreamer),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{streamerId}")
    public ResponseEntity<DeleteResponseDto> deleteStreamer(@PathVariable("streamerId") String streamerId) {
        String result = streamerService.deleteStreamer(streamerId);
        DeleteResponseDto responseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
