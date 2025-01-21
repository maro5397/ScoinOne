package com.scoinone.streamer.unit.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.repository.VirtualAssetRepository;
import com.scoinone.streamer.service.impl.VirtualAssetServiceImpl;
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
    private static final String testVirtualAssetId = "aaaaaaaa-aaaa-aaaa-aaaa-virtualasset";

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
        List<VirtualAssetEntity> virtualAssets = new ArrayList<>();
        when(virtualAssetRepository.findAll()).thenReturn(virtualAssets);

        List<VirtualAssetEntity> result = virtualAssetService.getVirtualAssets();

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).isEqualTo(virtualAssets);
            verify(virtualAssetRepository).findAll();
        });
    }

    @Test
    @DisplayName("인덱스로 가상자산 조회하기 테스트")
    public void testGetVirtualAssetById_Success() {
        VirtualAssetEntity virtualAsset = VirtualAssetEntity.builder()
                .id(testVirtualAssetId)
                .build();
        when(virtualAssetRepository.findById(testVirtualAssetId)).thenReturn(Optional.of(virtualAsset));

        VirtualAssetEntity result = virtualAssetService.getVirtualAssetById(testVirtualAssetId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(virtualAssetRepository).findById(testVirtualAssetId);
        });
    }

    @Test
    @DisplayName("인덱스로 가상자산 조회하기 실패")
    public void testGetVirtualAssetById_NotFound() {
        when(virtualAssetRepository.findById(testVirtualAssetId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> virtualAssetService.getVirtualAssetById(testVirtualAssetId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("VirtualAsset not found with id: " + testVirtualAssetId);
        });
    }

    @Test
    @DisplayName("가상자산 생성 테스트")
    public void testCreateVirtualAsset() {
        String name = "Bitcoin";
        String symbol = "BTC";
        String description = "Digital currency";

        virtualAssetService.createVirtualAsset(name, symbol, description);

        ArgumentCaptor<VirtualAssetEntity> virtualAssetCaptor = forClass(VirtualAssetEntity.class);
        verify(virtualAssetRepository).save(virtualAssetCaptor.capture());

        VirtualAssetEntity virtualAsset = virtualAssetCaptor.getValue();

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
        VirtualAssetEntity existingAsset = VirtualAssetEntity.builder()
                .name("Old Name")
                .description("Old Description")
                .symbol("OLD")
                .build();

        when(virtualAssetRepository.findById(testVirtualAssetId)).thenReturn(Optional.of(existingAsset));

        VirtualAssetEntity result = virtualAssetService.updateVirtualAsset(
                testVirtualAssetId,
                "New Name",
                "NEW",
                "New Description"
        );

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getName()).isEqualTo("New Name");
            softly.assertThat(result.getSymbol()).isEqualTo("NEW");
            verify(virtualAssetRepository).findById(testVirtualAssetId);
        });
    }

    @Test
    @DisplayName("가상자산 삭제 테스트")
    public void testDeleteVirtualAsset() {
        virtualAssetService.deleteVirtualAsset(testVirtualAssetId);

        verify(virtualAssetRepository).deleteById(testVirtualAssetId);
    }
}