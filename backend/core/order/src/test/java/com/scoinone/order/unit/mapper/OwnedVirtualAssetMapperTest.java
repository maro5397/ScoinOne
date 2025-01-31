package com.scoinone.order.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.dto.response.ownedvirtualasset.GetOwnedAssetResponseDto;
import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import com.scoinone.order.mapper.OwnedVirtualAssetMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class OwnedVirtualAssetMapperTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testOwnedVirtualAssetId1 = "aaaaaaaa-aaaa-aaow-nedv-irtualasset1";
    private static final String testOwnedVirtualAssetId2 = "aaaaaaaa-aaaa-aaow-nedv-irtualasset2";
    private static final String testVirtualAssetId = "aaaaaaaa-aaaa-aaaa-aaaa-virtualasset";

    private OwnedVirtualAssetMapper mapper;
    private List<OwnedVirtualAssetEntity> ownedVirtualAssets;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(OwnedVirtualAssetMapper.class);
        ownedVirtualAssets = Arrays.asList(
                createOwnedVirtualAsset(testOwnedVirtualAssetId1, testUserId, testVirtualAssetId),
                createOwnedVirtualAsset(testOwnedVirtualAssetId2, testUserId, testVirtualAssetId)
        );
    }

    @Test
    @DisplayName("보유 가상자산 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testOwnedVirtualAssetToGetOwnedAssetResponseDto() {
        OwnedVirtualAssetEntity ownedVirtualAsset = ownedVirtualAssets.getFirst();

        GetOwnedAssetResponseDto responseDto = mapper.ownedVirtualAssetToGetOwnedAssetResponseDto(ownedVirtualAsset);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOwnedVirtualAssetId()).isEqualTo(testOwnedVirtualAssetId1);
            softly.assertThat(responseDto.getUserId()).isEqualTo(testUserId);
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(testVirtualAssetId);
            softly.assertThat(responseDto.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(100L));
        });
    }

    private OwnedVirtualAssetEntity createOwnedVirtualAsset(String id, String userId, String virtualAssetId) {
        return OwnedVirtualAssetEntity.builder()
                .id(id)
                .virtualAssetId(virtualAssetId)
                .userId(userId)
                .amount(BigDecimal.valueOf(100))
                .build();
    }
}