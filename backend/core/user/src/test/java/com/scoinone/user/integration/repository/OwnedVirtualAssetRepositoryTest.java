package com.scoinone.user.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.user.config.TestContainerConfig;
import com.scoinone.user.entity.OwnedVirtualAssetEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.OwnedVirtualAssetRepository;
import com.scoinone.user.repository.UserRepository;
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
    private static final String testUsername = "testUsername";

    private UserEntity user;

    @Autowired
    private OwnedVirtualAssetRepository ownedVirtualAssetRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .username(testUsername)
                .email("user@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        OwnedVirtualAssetEntity ownedVirtualAsset = OwnedVirtualAssetEntity.builder()
                .user(user)
                .virtualAssetId(testVirtualAssetId)
                .amount(BigDecimal.valueOf(10))
                .build();
        ownedVirtualAssetRepository.save(ownedVirtualAsset);
    }

    @AfterEach
    void tearDown() {
        ownedVirtualAssetRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id() {
        String userId = user.getId();

        List<OwnedVirtualAssetEntity> ownedAssets = ownedVirtualAssetRepository.findByUser_Id(userId);

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(1);
            softly.assertThat(ownedAssets.getFirst().getUser().getId()).isEqualTo(userId);
            softly.assertThat(ownedAssets.getFirst().getVirtualAssetId()).isEqualTo(testVirtualAssetId);
            softly.assertThat(ownedAssets.getFirst().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(10));
        });
    }

    @Test
    @DisplayName("사용자 ID와 가상자산 ID로 보유 가상 자산 조회")
    void testFindByUser_IdAndVirtualAsset_Id() {
        String userId = user.getId();

        Optional<OwnedVirtualAssetEntity> foundOwnedVirtualAsset = ownedVirtualAssetRepository
                .findByUser_IdAndVirtualAssetId(
                        userId,
                        testVirtualAssetId
                );

        assertSoftly(softly -> {
            softly.assertThat(foundOwnedVirtualAsset.isPresent()).isTrue();
            foundOwnedVirtualAsset.ifPresent(ownedVirtualAsset -> {
                softly.assertThat(ownedVirtualAsset.getVirtualAssetId()).isEqualTo(testVirtualAssetId);
                softly.assertThat(ownedVirtualAsset.getUser().getId()).isEqualTo(userId);
            });
        });
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id_NotFound() {
        List<OwnedVirtualAssetEntity> ownedAssets = ownedVirtualAssetRepository
                .findByUser_Id("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaauser");

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(0);
        });
    }
}