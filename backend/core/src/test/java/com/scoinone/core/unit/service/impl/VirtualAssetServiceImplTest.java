package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.impl.VirtualAssetServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class VirtualAssetServiceImplTest {
    @InjectMocks
    private VirtualAssetServiceImpl virtualAssetService;

    @Mock
    private VirtualAssetRepository virtualAssetRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("가상자산 리스트 조회하기 테스트")
    public void testGetVirtualAssets() {
        List<VirtualAsset> virtualAssets = new ArrayList<>();
        when(virtualAssetRepository.findAll()).thenReturn(virtualAssets);

        List<VirtualAsset> result = virtualAssetService.getVirtualAssets();

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).isEqualTo(virtualAssets);
            verify(virtualAssetRepository).findAll();
        });
    }

    @Test
    @DisplayName("인덱스로 가상자산 조회하기 테스트")
    public void testGetVirtualAssetById_Success() {
        Long assetId = 1L;
        VirtualAsset virtualAsset = VirtualAsset.builder()
                .id(assetId)
                .build();
        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.of(virtualAsset));

        VirtualAsset result = virtualAssetService.getVirtualAssetById(assetId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(virtualAssetRepository).findById(assetId);
        });
    }

    @Test
    @DisplayName("인덱스로 가상자산 조회하기 실패")
    public void testGetVirtualAssetById_NotFound() {
        Long assetId = 1L;
        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> virtualAssetService.getVirtualAssetById(assetId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("VirtualAsset not found with id: " + assetId);
        });
    }

    @Test
    @DisplayName("가상자산 생성 테스트")
    public void testCreateVirtualAsset() {
        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name("Bitcoin")
                .description("Digital currency")
                .symbol("BTC")
                .build();

        when(virtualAssetRepository.save(virtualAsset)).thenReturn(virtualAsset);

        VirtualAsset result = virtualAssetService.createVirtualAsset(virtualAsset);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getName()).isEqualTo("Bitcoin");
            softly.assertThat(result.getSymbol()).isEqualTo("BTC");
            verify(virtualAssetRepository).save(virtualAsset);
        });
    }

    @Test
    @DisplayName("가상자산 수정 테스트")
    public void testUpdateVirtualAsset() {
        Long assetId = 1L;
        VirtualAsset existingAsset = VirtualAsset.builder()
                .name("Old Name")
                .description("Old Description")
                .symbol("OLD")
                .build();

        VirtualAsset updatedAsset = VirtualAsset.builder()
                .name("New Name")
                .description("New Description")
                .symbol("NEW")
                .build();

        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.of(existingAsset));
        when(virtualAssetRepository.save(existingAsset)).thenReturn(existingAsset);

        VirtualAsset result = virtualAssetService.updateVirtualAsset(assetId, updatedAsset);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getName()).isEqualTo("New Name");
            softly.assertThat(result.getSymbol()).isEqualTo("NEW");
            verify(virtualAssetRepository).findById(assetId);
            verify(virtualAssetRepository).save(existingAsset);
        });
    }

    @Test
    @DisplayName("가상자산 삭제 테스트")
    public void testDeleteVirtualAsset() {
        Long assetId = 1L;

        virtualAssetService.deleteVirtualAsset(assetId);

        verify(virtualAssetRepository).deleteById(assetId);
    }
}