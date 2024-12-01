package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.virtualasset.CreateVirtualAssetRequestDto;
import com.scoinone.core.dto.request.virtualasset.UpdateVirtualAssetRequestDto;
import com.scoinone.core.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetsResponseDto;
import com.scoinone.core.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.VirtualAssetService;
import com.scoinone.core.config.UserDataInitializer;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class, UserDataInitializer.class})
@ActiveProfiles("dev")
class VirtualAssetControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private VirtualAsset savedVirtualAsset;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired VirtualAssetService virtualAssetService,
            @Autowired AuthService authService
    ) {
        String jwtToken = authService.authenticate("admin@example.com", "password");

        savedVirtualAsset = virtualAssetService.createVirtualAsset("Bitcoin", "BTC", "Digital currency");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired VirtualAssetRepository virtualAssetRepository) {
        virtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("가상 자산 생성 테스트")
    void createVirtualAsset_shouldReturnCreatedVirtualAsset() {
        CreateVirtualAssetRequestDto requestDto = new CreateVirtualAssetRequestDto();
        requestDto.setName("Ethereum");
        requestDto.setSymbol("ETH");
        requestDto.setDescription("Another digital currency");

        ResponseEntity<CreateVirtualAssetResponseDto> response = restTemplate.exchange(
                "/api/assets",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateVirtualAssetResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Ethereum");
            softly.assertThat(response.getBody().getSymbol()).isEqualTo("ETH");
            softly.assertThat(response.getBody().getDescription()).isEqualTo("Another digital currency");
        });
    }

    @Test
    @DisplayName("가상 자산 목록 조회 테스트")
    void getVirtualAssets_shouldReturnListOfVirtualAssets() {
        ResponseEntity<GetVirtualAssetsResponseDto> response = restTemplate.exchange(
                "/api/assets",
                HttpMethod.GET,
                null,
                GetVirtualAssetsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getVirtualAssets()).hasSize(1);
        });
    }

    @Test
    @DisplayName("가상 자산 조회 테스트")
    void getVirtualAsset_shouldReturnVirtualAsset() {
        ResponseEntity<GetVirtualAssetResponseDto> response = restTemplate.exchange(
                "/api/assets/" + savedVirtualAsset.getId(),
                HttpMethod.GET,
                null,
                GetVirtualAssetResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Bitcoin");
            softly.assertThat(response.getBody().getSymbol()).isEqualTo("BTC");
        });
    }

    @Test
    @DisplayName("가상 자산 수정 테스트")
    void updateVirtualAsset_shouldReturnUpdatedVirtualAsset() {
        UpdateVirtualAssetRequestDto updateRequestDto = new UpdateVirtualAssetRequestDto();
        updateRequestDto.setName("Ripple Updated");
        updateRequestDto.setSymbol("XRP");
        updateRequestDto.setDescription("Updated description");

        ResponseEntity<UpdateVirtualAssetResponseDto> response = restTemplate.exchange(
                "/api/assets/" + savedVirtualAsset.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateVirtualAssetResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Ripple Updated");
            softly.assertThat(response.getBody().getDescription()).isEqualTo("Updated description");
        });
    }

    @Test
    @DisplayName("가상 자산 삭제 테스트")
    void deleteVirtualAsset_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/assets/" + savedVirtualAsset.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("VirtualAsset deleted successfully");
        });
    }
}