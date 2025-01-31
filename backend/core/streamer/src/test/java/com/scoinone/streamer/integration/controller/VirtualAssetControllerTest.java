package com.scoinone.streamer.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.streamer.config.TestContainerConfig;
import com.scoinone.streamer.dto.request.virtualasset.UpdateVirtualAssetRequestDto;
import com.scoinone.streamer.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.streamer.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
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
class VirtualAssetControllerTest {
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
                "WAK",
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
    @DisplayName("가상 자산 목록 조회 테스트")
    void getVirtualAssets_shouldReturnListOfVirtualAssets() {
        ResponseEntity<List<GetVirtualAssetResponseDto>> response = restTemplate.exchange(
                "/api/assets",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GetVirtualAssetResponseDto>>() {}
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("가상 자산 조회 테스트")
    void getVirtualAsset_shouldReturnVirtualAsset() {
        ResponseEntity<GetVirtualAssetResponseDto> response = restTemplate.exchange(
                "/api/assets/" + savedStreamer.getVirtualAsset().getId(),
                HttpMethod.GET,
                null,
                GetVirtualAssetResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("WakToken");
            softly.assertThat(response.getBody().getSymbol()).isEqualTo("WAK");
        });
    }

    @Test
    @DisplayName("가상 자산 수정 테스트")
    void updateVirtualAsset_shouldReturnUpdatedVirtualAsset() {
        UpdateVirtualAssetRequestDto updateRequestDto = new UpdateVirtualAssetRequestDto();
        updateRequestDto.setName("RATMonster");
        updateRequestDto.setSymbol("RAT");
        updateRequestDto.setDescription("괴물쥐 코인");

        ResponseEntity<UpdateVirtualAssetResponseDto> response = restTemplate.exchange(
                "/api/assets/" + savedStreamer.getVirtualAsset().getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateVirtualAssetResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("RATMonster");
            softly.assertThat(response.getBody().getDescription()).isEqualTo("괴물쥐 코인");
        });
    }
}