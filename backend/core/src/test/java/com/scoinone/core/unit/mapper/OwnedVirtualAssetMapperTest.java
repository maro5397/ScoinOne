package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

import com.scoinone.core.dto.response.user.GetOwnedAssetsResponseDto;
import com.scoinone.core.dto.response.user.GetOwnedAssetResponseDto;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.mapper.OwnedVirtualAssetMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class OwnedVirtualAssetMapperTest {
    private OwnedVirtualAssetMapper mapper;
    private List<OwnedVirtualAsset> ownedVirtualAssets;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(OwnedVirtualAssetMapper.class);
        ownedVirtualAssets = Arrays.asList(
                createOwnedVirtualAsset(1L, 2L, 3L),
                createOwnedVirtualAsset(4L, 5L, 6L)
        );
    }

    @Test
    @DisplayName("보유 가상자산 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testOwnedVirtualAssetToGetOwnedAssetResponseDto() {
        OwnedVirtualAsset ownedVirtualAsset = ownedVirtualAssets.getFirst();

        GetOwnedAssetResponseDto responseDto = mapper.ownedVirtualAssetToGetOwnedAssetResponseDto(ownedVirtualAsset);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getOwnedVirtualAssetId()).isEqualTo(1L);
            softly.assertThat(responseDto.getUserId()).isEqualTo(2L);
            softly.assertThat(responseDto.getVirtualAssetId()).isEqualTo(3L);
            softly.assertThat(responseDto.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(100L));
        });
    }

    @Test
    @DisplayName("보유 가상자산 엔티티 객체를 리스트 조회용 응답 DTO로 매핑")
    public void testListToGetOwnedAssetListResponseDto() {
        GetOwnedAssetsResponseDto responseDto = mapper.listToGetOwnedAssetListResponseDto(ownedVirtualAssets);

        assertEquals(2, responseDto.getOwnedAssets().size());
        assertEquals(1L, responseDto.getOwnedAssets().get(0).getOwnedVirtualAssetId());
        assertEquals(4L, responseDto.getOwnedAssets().get(1).getOwnedVirtualAssetId());
    }

    private OwnedVirtualAsset createOwnedVirtualAsset(Long id, Long userId, Long virtualAssetId) {
        VirtualAsset virtualAsset = VirtualAsset.builder()
                .id(virtualAssetId)
                .build();

        User user = User.builder()
                .id(userId)
                .build();

        return OwnedVirtualAsset.builder()
                .virtualAsset(virtualAsset)
                .user(user)
                .id(id)
                .amount(BigDecimal.valueOf(100))
                .build();
    }
}