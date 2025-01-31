package com.scoinone.streamer.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.streamer.dto.response.streamer.CreateStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.GetStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.UpdateStreamerResponseDto;
import com.scoinone.streamer.entity.StreamerEntity;
import com.scoinone.streamer.mapper.StreamerMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class StreamerMapperTest {
    private static final String TEST_STREAMER_ID_1 = "aaaaaaaa-aaaa-aaaa-aaaa-aaastreamer1";
    private static final String TEST_STREAMER_ID_2 = "aaaaaaaa-aaaa-aaaa-aaaa-aaastreamer2";

    private StreamerMapper mapper;
    private List<StreamerEntity> streamerEntities;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(StreamerMapper.class);
        streamerEntities = Arrays.asList(
                createStreamerEntity(
                        TEST_STREAMER_ID_1,
                        "soop",
                        "ecvhao",
                        "우왁굳",
                        "@woowakgood",
                        "steamindiegame"),
                createStreamerEntity(
                        TEST_STREAMER_ID_2,
                        "chzzk",
                        "c7ded8ea6b0605d3c78e18650d2df83b",
                        "괴물쥐",
                        "@괴물쥐유튜브",
                        "tmxk9999")
        );
    }

    @Test
    @DisplayName("스트리머 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testStreamerToGetStreamerResponseDto() {
        StreamerEntity streamer = streamerEntities.getFirst();
        GetStreamerResponseDto responseDto = mapper.streamerToGetStreamerResponseDto(streamer);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getStreamerId()).isEqualTo(TEST_STREAMER_ID_1);
            softly.assertThat(responseDto.getLiveStreamingPlatform()).isEqualTo("soop");
            softly.assertThat(responseDto.getLiveStreamingPlatformStreamerId()).isEqualTo("ecvhao");
            softly.assertThat(responseDto.getSearchKeyword()).isEqualTo("우왁굳");
        });
    }

    @Test
    @DisplayName("스트리머 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testStreamerToCreateStreamerResponseDto() {
        StreamerEntity streamer = streamerEntities.getFirst();
        CreateStreamerResponseDto responseDto = mapper.streamerToCreateStreamerResponseDto(streamer);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getStreamerId()).isEqualTo(TEST_STREAMER_ID_1);
            softly.assertThat(responseDto.getLiveStreamingPlatform()).isEqualTo("soop");
            softly.assertThat(responseDto.getLiveStreamingPlatformStreamerId()).isEqualTo("ecvhao");
        });
    }

    @Test
    @DisplayName("스트리머 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testStreamerToUpdateStreamerResponseDto() {
        StreamerEntity streamer = streamerEntities.getFirst();
        UpdateStreamerResponseDto responseDto = mapper.streamerToUpdateStreamerResponseDto(streamer);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getStreamerId()).isEqualTo(TEST_STREAMER_ID_1);
        });
    }

    private StreamerEntity createStreamerEntity(
            String id,
            String livePlatform,
            String livePlatformStreamerId,
            String searchKeyword,
            String youtubeId,
            String naverCafeId
    ) {
        return StreamerEntity.builder()
                .id(id)
                .liveStreamingPlatform(livePlatform)
                .liveStreamingPlatformStreamerId(livePlatformStreamerId)
                .searchKeyword(searchKeyword)
                .youtubeId(youtubeId)
                .naverCafeId(naverCafeId)
                .build();
    }
}