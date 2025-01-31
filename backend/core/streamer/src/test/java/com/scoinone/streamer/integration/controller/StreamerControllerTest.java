package com.scoinone.streamer.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.streamer.config.TestContainerConfig;
import com.scoinone.streamer.dto.common.DeleteResponseDto;
import com.scoinone.streamer.dto.request.streamer.CreateStreamerRequestDto;
import com.scoinone.streamer.dto.request.streamer.CreateStreamerRequestDto.CreateVirtualAssetRequestDto;
import com.scoinone.streamer.dto.request.streamer.UpdateStreamerRequestDto;
import com.scoinone.streamer.dto.response.streamer.CreateStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.GetStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.UpdateStreamerResponseDto;
import com.scoinone.streamer.entity.StreamerEntity;
import com.scoinone.streamer.repository.StreamerRepository;
import com.scoinone.streamer.service.StreamerService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class})
@ActiveProfiles("dev")
class StreamerControllerTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

    @Autowired
    private TestRestTemplate restTemplate;

    private StreamerEntity savedStreamer;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(@Autowired StreamerService streamerService) {
        savedStreamer = streamerService.createStreamer(
                "soop",
                "ecvhao",
                "우왁굳",
                "@woowakgood",
                "steamindiegame",
                "WakToken",
                "Wak",
                "우왁굳 코인"
        );

        headers = new HttpHeaders();
        headers.set("UserId", testUserId);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired StreamerRepository streamerRepository) {
        streamerRepository.deleteAll();
    }

    @Test
    @DisplayName("스트리머 생성 테스트")
    void createStreamer_shouldReturnCreatedStreamer() {
        CreateVirtualAssetRequestDto innerRequestDto = new CreateVirtualAssetRequestDto();
        innerRequestDto.setName("RATMonster");
        innerRequestDto.setSymbol("RAT");
        innerRequestDto.setDescription("괴물쥐 코인");
        CreateStreamerRequestDto requestDto = new CreateStreamerRequestDto();
        requestDto.setVirtualAsset(innerRequestDto);
        requestDto.setLiveStreamingPlatform("chzzk");
        requestDto.setLiveStreamingPlatformStreamerId("c7ded8ea6b0605d3c78e18650d2df83b");
        requestDto.setSearchKeyword("괴물쥐");
        requestDto.setYoutubeId("@괴물쥐유튜브");
        requestDto.setNaverCafeId("tmxk9999");


        ResponseEntity<CreateStreamerResponseDto> response = restTemplate.exchange(
                "/api/streamer",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateStreamerResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getLiveStreamingPlatform())
                    .isEqualTo("chzzk");
            softly.assertThat(response.getBody().getLiveStreamingPlatformStreamerId())
                    .isEqualTo("c7ded8ea6b0605d3c78e18650d2df83b");
            softly.assertThat(response.getBody().getSearchKeyword())
                    .isEqualTo("괴물쥐");
            softly.assertThat(response.getBody().getYoutubeId())
                    .isEqualTo("@괴물쥐유튜브");
            softly.assertThat(response.getBody().getNaverCafeId())
                    .isEqualTo("tmxk9999");
            softly.assertThat(response.getBody().getVirtualAsset().getName())
                    .isEqualTo("RATMonster");
            softly.assertThat(response.getBody().getVirtualAsset().getSymbol())
                    .isEqualTo("RAT");
            softly.assertThat(response.getBody().getVirtualAsset().getDescription())
                    .isEqualTo("괴물쥐 코인");
        });
    }

    @Test
    @DisplayName("스트리머 목록 조회 테스트")
    void getStreamers_shouldReturnListOfStreamers() {
        ResponseEntity<List<GetStreamerResponseDto>> response = restTemplate.exchange(
                "/api/streamer",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GetStreamerResponseDto>>() {}
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("스트리머 조회 테스트")
    void getStreamer_shouldReturnStreamer() {
        ResponseEntity<GetStreamerResponseDto> response = restTemplate.exchange(
                "/api/streamer/" + savedStreamer.getId(),
                HttpMethod.GET,
                null,
                GetStreamerResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getLiveStreamingPlatform())
                    .isEqualTo("soop");
            softly.assertThat(response.getBody().getLiveStreamingPlatformStreamerId())
                    .isEqualTo("ecvhao");
            softly.assertThat(response.getBody().getSearchKeyword())
                    .isEqualTo("우왁굳");
            softly.assertThat(response.getBody().getYoutubeId())
                    .isEqualTo("@woowakgood");
            softly.assertThat(response.getBody().getNaverCafeId())
                    .isEqualTo("steamindiegame");
        });
    }

    @Test
    @DisplayName("스트리머 수정 테스트")
    void updateStreamer_shouldReturnUpdatedStreamer() {
        UpdateStreamerRequestDto updateRequestDto = new UpdateStreamerRequestDto();
        updateRequestDto.setLiveStreamingPlatform("chzzk");
        updateRequestDto.setLiveStreamingPlatformStreamerId("c7ded8ea6b0605d3c78e18650d2df83b");
        updateRequestDto.setSearchKeyword("괴물쥐");
        updateRequestDto.setYoutubeId("@괴물쥐유튜브");
        updateRequestDto.setNaverCafeId("tmxk9999");

        ResponseEntity<UpdateStreamerResponseDto> response = restTemplate.exchange(
                "/api/streamer/" + savedStreamer.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateStreamerResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getLiveStreamingPlatform())
                    .isEqualTo("chzzk");
            softly.assertThat(response.getBody().getLiveStreamingPlatformStreamerId())
                    .isEqualTo("c7ded8ea6b0605d3c78e18650d2df83b");
            softly.assertThat(response.getBody().getSearchKeyword())
                    .isEqualTo("괴물쥐");
            softly.assertThat(response.getBody().getYoutubeId())
                    .isEqualTo("@괴물쥐유튜브");
            softly.assertThat(response.getBody().getNaverCafeId())
                    .isEqualTo("tmxk9999");
        });
    }

    @Test
    @DisplayName("스트리머 삭제 테스트")
    void deleteStreamer_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/streamer/" + savedStreamer.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("Streamer/VirtualAsset deleted successfully");
        });
    }
}