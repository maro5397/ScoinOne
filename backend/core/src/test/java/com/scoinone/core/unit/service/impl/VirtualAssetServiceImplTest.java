package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.Comment;
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
import org.mockito.ArgumentCaptor;
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
        String name = "Bitcoin";
        String symbol = "BTC";
        String description = "Digital currency";

        virtualAssetService.createVirtualAsset(name, symbol, description);

        ArgumentCaptor<VirtualAsset> virtualAssetCaptor = forClass(VirtualAsset.class);
        verify(virtualAssetRepository).save(virtualAssetCaptor.capture());

        VirtualAsset virtualAsset = virtualAssetCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(virtualAsset).isNotNull();
            softly.assertThat(virtualAsset.getName()).isEqualTo("Bitcoin");
            softly.assertThat(virtualAsset.getSymbol()).isEqualTo("BTC");
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

        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.of(existingAsset));

        VirtualAsset result = virtualAssetService.updateVirtualAsset(
                assetId,
                "New Name",
                "NEW",
                "New Description"
        );

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getName()).isEqualTo("New Name");
            softly.assertThat(result.getSymbol()).isEqualTo("NEW");
            verify(virtualAssetRepository).findById(assetId);
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