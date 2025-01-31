package com.scoinone.streamer.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.streamer.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.streamer.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.streamer.entity.VirtualAssetEntity;
import com.scoinone.streamer.mapper.VirtualAssetMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class VirtualAssetMapperTest {
    private static final String testVirtualAssetId1 = "aaaaaaaa-aaaa-aaaa-aaav-irtualasset1";
    private static final String testVirtualAssetId2 = "aaaaaaaa-aaaa-aaaa-aaav-irtualasset2";
    private static final String testVirtualAssetId3 = "aaaaaaaa-aaaa-aaaa-aaav-irtualasset3";

    private VirtualAssetMapper mapper;
    private List<VirtualAssetEntity> virtualAssets;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(VirtualAssetMapper.class);
        virtualAssets = Arrays.asList(
                createVirtualAsset(testVirtualAssetId1, "Bitcoin", "BTC", "Bitcoin Description"),
                createVirtualAsset(testVirtualAssetId2, "Ethereum", "ETH", "Ethereum Description"),
                createVirtualAsset(testVirtualAssetId3, "Solana", "SOL", "Solana Description")
        );
    }

    @Test
    @DisplayName("가상자산 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testVirtualAssetToGetVirtualAssetResponseDto() {
        VirtualAssetEntity virtualAsset = virtualAssets.getFirst();
        GetVirtualAssetResponseDto responseDto = mapper.virtualAssetToGetVirtualAssetResponseDto(virtualAsset);
        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(testVirtualAssetId1);
            softly.assertThat(responseDto.getName()).isEqualTo("Bitcoin");
            softly.assertThat(responseDto.getSymbol()).isEqualTo("BTC");
            softly.assertThat(responseDto.getDescription()).isEqualTo("Bitcoin Description");
        });
    }

    @Test
    @DisplayName("가상자산 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testVirtualAssetToUpdateVirtualAssetResponseDto() {
        VirtualAssetEntity virtualAsset = virtualAssets.getFirst();
        UpdateVirtualAssetResponseDto responseDto = mapper.virtualAssetToUpdateVirtualAssetResponseDto(virtualAsset);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(testVirtualAssetId1);
            softly.assertThat(responseDto.getName()).isEqualTo("Bitcoin");
            softly.assertThat(responseDto.getSymbol()).isEqualTo("BTC");
            softly.assertThat(responseDto.getDescription()).isEqualTo("Bitcoin Description");
        });
    }

    private VirtualAssetEntity createVirtualAsset(
            String id,
            String name,
            String symbol,
            String description
    ) {
        return VirtualAssetEntity.builder()
                .id(id)
                .name(name)
                .symbol(symbol)
                .description(description)
                .build();
    }
}