package com.scoinone.order.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import com.scoinone.order.repository.OwnedVirtualAssetRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
@ActiveProfiles("dev")
class OwnedVirtualAssetRepositoryTest {
    private static final String testVirtualAssetId = "aaaaaaaa-aaaa-aaaa-aaaa-virtualasset";
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

    @Autowired
    private OwnedVirtualAssetRepository ownedVirtualAssetRepository;

    @BeforeEach
    void setUp() {
        OwnedVirtualAssetEntity ownedVirtualAsset = OwnedVirtualAssetEntity.builder()
                .userId(testUserId)
                .virtualAssetId(testVirtualAssetId)
                .amount(BigDecimal.valueOf(10))
                .build();
        ownedVirtualAssetRepository.save(ownedVirtualAsset);
    }

    @AfterEach
    void tearDown() {
        ownedVirtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id() {
        List<OwnedVirtualAssetEntity> ownedAssets = ownedVirtualAssetRepository.findByUserId(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(1);
            softly.assertThat(ownedAssets.getFirst().getUserId()).isEqualTo(testUserId);
            softly.assertThat(ownedAssets.getFirst().getVirtualAssetId()).isEqualTo(testVirtualAssetId);
            softly.assertThat(ownedAssets.getFirst().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(10));
        });
    }

    @Test
    @DisplayName("사용자 ID와 가상자산 ID로 보유 가상 자산 조회")
    void testFindByUser_IdAndVirtualAsset_Id() {
        Optional<OwnedVirtualAssetEntity> foundOwnedVirtualAsset = ownedVirtualAssetRepository
                .findByUserIdAndVirtualAssetId(
                        testUserId,
                        testVirtualAssetId
                );

        assertSoftly(softly -> {
            softly.assertThat(foundOwnedVirtualAsset.isPresent()).isTrue();
            foundOwnedVirtualAsset.ifPresent(ownedVirtualAsset -> {
                softly.assertThat(ownedVirtualAsset.getVirtualAssetId()).isEqualTo(testVirtualAssetId);
                softly.assertThat(ownedVirtualAsset.getUserId()).isEqualTo(testUserId);
            });
        });
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id_NotFound() {
        List<OwnedVirtualAssetEntity> ownedAssets = ownedVirtualAssetRepository
                .findByUserId("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaauser");

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(0);
        });
    }
}