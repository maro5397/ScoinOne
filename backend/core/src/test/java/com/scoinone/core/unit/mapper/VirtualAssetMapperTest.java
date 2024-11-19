package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetListResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.mapper.VirtualAssetMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class VirtualAssetMapperTest {
    private VirtualAssetMapper mapper;
    private List<VirtualAsset> virtualAssets;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(VirtualAssetMapper.class);
        virtualAssets = Arrays.asList(
                createVirtualAsset(1L, "Bitcoin", "BTC", "Bitcoin Description",
                        LocalDateTime.parse("2023-11-19T10:15:30")),
                createVirtualAsset(2L, "Ethereum", "ETH", "Ethereum Description",
                        LocalDateTime.parse("2023-11-19T11:00:00")),
                createVirtualAsset(3L, "Solana", "SOL", "Solana Description",
                        LocalDateTime.parse("2023-11-19T12:00:00"))
        );
    }

    @Test
    @DisplayName("가상자산 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testVirtualAssetToGetVirtualAssetResponseDto() {
        VirtualAsset virtualAsset = virtualAssets.getFirst();
        GetVirtualAssetResponseDto responseDto = mapper.virtualAssetToGetVirtualAssetResponseDto(virtualAsset);
        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(1L);
            softly.assertThat(responseDto.getName()).isEqualTo("Bitcoin");
            softly.assertThat(responseDto.getSymbol()).isEqualTo("BTC");
            softly.assertThat(responseDto.getDescription()).isEqualTo("Bitcoin Description");
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo("2023-11-19T10:15:30");
        });
    }

    @Test
    @DisplayName("가상자산 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testVirtualAssetToCreateVirtualAssetResponseDto() {
        VirtualAsset virtualAsset = virtualAssets.getFirst();
        CreateVirtualAssetResponseDto responseDto = mapper.virtualAssetToCreateVirtualAssetResponseDto(virtualAsset);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(1L);
            softly.assertThat(responseDto.getName()).isEqualTo("Bitcoin");
            softly.assertThat(responseDto.getSymbol()).isEqualTo("BTC");
            softly.assertThat(responseDto.getDescription()).isEqualTo("Bitcoin Description");
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo("2023-11-19T10:15:30");
        });
    }

    @Test
    @DisplayName("가상자산 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testVirtualAssetToUpdateVirtualAssetResponseDto() {
        VirtualAsset virtualAsset = virtualAssets.getFirst();
        UpdateVirtualAssetResponseDto responseDto = mapper.virtualAssetToUpdateVirtualAssetResponseDto(virtualAsset);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(1L);
            softly.assertThat(responseDto.getName()).isEqualTo("Bitcoin");
            softly.assertThat(responseDto.getSymbol()).isEqualTo("BTC");
            softly.assertThat(responseDto.getDescription()).isEqualTo("Bitcoin Description");
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo("2023-11-19T10:15:30");
        });
    }

    @Test
    @DisplayName("다수의 가상자산 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testListToGetVirtualAssetListResponseDto() {
        GetVirtualAssetListResponseDto responseDto = mapper.listToGetVirtualAssetListResponseDto(virtualAssets);
        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssets()).hasSize(3);
            softly.assertThat(responseDto.getVirtualAssets().get(0).getVirtualAssetId()).isEqualTo(1L);
            softly.assertThat(responseDto.getVirtualAssets().get(1).getVirtualAssetId()).isEqualTo(2L);
            softly.assertThat(responseDto.getVirtualAssets().get(2).getVirtualAssetId()).isEqualTo(3L);
        });
    }

    private VirtualAsset createVirtualAsset(
            Long id,
            String name,
            String symbol,
            String description,
            LocalDateTime createdAt
    ) {
        return VirtualAsset.builder()
                .id(id)
                .name(name)
                .symbol(symbol)
                .description(description)
                .createdAt(createdAt)
                .build();
    }
}