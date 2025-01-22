package com.scoinone.streamer.unit.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.streamer.entity.StreamerEntity;
import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.repository.StreamerRepository;
import com.scoinone.streamer.repository.VirtualAssetRepository;
import com.scoinone.streamer.service.impl.StreamerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StreamerServiceImplTest {
    private static final String testStreamerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaastreamer";

    @InjectMocks
    private StreamerServiceImpl streamerService;

    @Mock
    private StreamerRepository streamerRepository;

    private final String liveStreamingPlatform = "soop";
    private final String liveStreamingPlatformStreamerId = "ecvhao";
    private final String searchKeyword = "우왁굳";
    private final String youtubeId = "@woowakgood";
    private final String naverCafeId = "steamindiegame";
    private final String virtualAssetName = "WakToken";
    private final String virtualAssetSymbol = "Wak";
    private final String virtualAssetDescription = "우왁굳 코인";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("가상자산 리스트 조회하기 테스트")
    public void testGetStreamers() {
        List<StreamerEntity> streamers = new ArrayList<>();
        when(streamerRepository.findAll()).thenReturn(streamers);

        List<StreamerEntity> result = streamerService.getStreamers();

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).isEqualTo(streamers);
            verify(streamerRepository).findAll();
        });
    }

    @Test
    @DisplayName("인덱스로 스트리머 조회하기 테스트")
    public void testGetStreamerById_Success() {
        StreamerEntity streamer = StreamerEntity.builder()
                .id(testStreamerId)
                .build();
        when(streamerRepository.findById(testStreamerId)).thenReturn(Optional.of(streamer));

        StreamerEntity result = streamerService.getStreamerById(testStreamerId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(streamerRepository).findById(testStreamerId);
        });
    }

    @Test
    @DisplayName("인덱스로 스트리머 조회하기 실패")
    public void testGetStreamerById_NotFound() {
        when(streamerRepository.findById(testStreamerId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> streamerService.getStreamerById(testStreamerId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Streamer not found with id: " + testStreamerId);
        });
    }

    @Test
    @DisplayName("스트리머 생성 테스트")
    public void testCreateStreamer() {
        streamerService.createStreamer(
                liveStreamingPlatform,
                liveStreamingPlatformStreamerId,
                searchKeyword,
                youtubeId,
                naverCafeId,
                virtualAssetName,
                virtualAssetSymbol,
                virtualAssetDescription
        );

        ArgumentCaptor<StreamerEntity> streamerArgumentCaptor = forClass(StreamerEntity.class);
        verify(streamerRepository).save(streamerArgumentCaptor.capture());

        StreamerEntity streamer = streamerArgumentCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(streamer).isNotNull();
            softly.assertThat(streamer.getLiveStreamingPlatform()).isEqualTo(liveStreamingPlatform);
            softly.assertThat(streamer.getLiveStreamingPlatformStreamerId()).isEqualTo(liveStreamingPlatformStreamerId);
            softly.assertThat(streamer.getSearchKeyword()).isEqualTo(searchKeyword);
            softly.assertThat(streamer.getYoutubeId()).isEqualTo(youtubeId);
            softly.assertThat(streamer.getNaverCafeId()).isEqualTo(naverCafeId);
            softly.assertThat(streamer.getVirtualAsset().getName()).isEqualTo(virtualAssetName);
            softly.assertThat(streamer.getVirtualAsset().getSymbol()).isEqualTo(virtualAssetSymbol);
            softly.assertThat(streamer.getVirtualAsset().getDescription()).isEqualTo(virtualAssetDescription);
            verify(streamerRepository).save(streamer);
        });
    }

    @Test
    @DisplayName("스트리머 수정 테스트")
    public void testUpdateStreamer() {
        StreamerEntity existingStreamer = StreamerEntity.builder()
                .id(testStreamerId)
                .liveStreamingPlatform(liveStreamingPlatform)
                .liveStreamingPlatformStreamerId(liveStreamingPlatformStreamerId)
                .searchKeyword(searchKeyword)
                .youtubeId(youtubeId)
                .naverCafeId(naverCafeId)
                .build();

        VirtualAssetEntity virtualAsset = VirtualAssetEntity.builder().build();

        when(streamerRepository.findById(testStreamerId)).thenReturn(Optional.of(existingStreamer));

        StreamerEntity result = streamerService.updateStreamer(
                testStreamerId,
                liveStreamingPlatform,
                liveStreamingPlatformStreamerId,
                searchKeyword,
                youtubeId,
                naverCafeId
        );

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getLiveStreamingPlatform()).isEqualTo(liveStreamingPlatform);
            softly.assertThat(result.getLiveStreamingPlatformStreamerId()).isEqualTo(liveStreamingPlatformStreamerId);
            softly.assertThat(result.getSearchKeyword()).isEqualTo(searchKeyword);
            softly.assertThat(result.getYoutubeId()).isEqualTo(youtubeId);
            softly.assertThat(result.getNaverCafeId()).isEqualTo(naverCafeId);
            verify(streamerRepository).findById(testStreamerId);
        });
    }

    @Test
    @DisplayName("스트리머 삭제 테스트")
    public void testDeleteStreamer() {
        streamerService.deleteStreamer(testStreamerId);

        verify(streamerRepository).deleteById(testStreamerId);
    }

}