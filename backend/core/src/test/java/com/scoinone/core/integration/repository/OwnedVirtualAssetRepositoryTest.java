package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.OwnedVirtualAssetRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = CoreApplication.class)
class OwnedVirtualAssetRepositoryTest {
    private User user;

    @Autowired
    private OwnedVirtualAssetRepository ownedVirtualAssetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VirtualAssetRepository virtualAssetRepository;

    @Container
    static MySQLContainer<?> sqlContainer = new MySQLContainer<>(
            "mysql:8.0.34"
    );

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", sqlContainer::getDriverClassName);
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.~username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("user@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name("Ethereum")
                .symbol("ETH")
                .description("A decentralized platform for applications")
                .build();
        virtualAssetRepository.save(virtualAsset);

        OwnedVirtualAsset ownedVirtualAsset = OwnedVirtualAsset.builder()
                .user(user)
                .virtualAsset(virtualAsset)
                .amount(BigDecimal.valueOf(10))
                .build();
        ownedVirtualAssetRepository.save(ownedVirtualAsset);
    }

    @AfterEach
    void tearDown() {
        ownedVirtualAssetRepository.deleteAll();
        virtualAssetRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id() {
        Long userId = user.getId();

        List<OwnedVirtualAsset> ownedAssets = ownedVirtualAssetRepository.findByUser_Id(userId);

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(1);
            softly.assertThat(ownedAssets.getFirst().getUser().getId()).isEqualTo(userId);
            softly.assertThat(ownedAssets.getFirst().getVirtualAsset().getName()).isEqualTo("Ethereum");
            softly.assertThat(ownedAssets.getFirst().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(10));
        });
    }

    @Test
    @DisplayName("존재하지 않는 사용자 ID로 보유 가상 자산 조회")
    void testFindByUser_Id_NotFound() {
        List<OwnedVirtualAsset> ownedAssets = ownedVirtualAssetRepository.findByUser_Id(999L);

        assertSoftly(softly -> {
            softly.assertThat(ownedAssets).hasSize(0);
        });
    }
}