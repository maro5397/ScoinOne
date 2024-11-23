package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class SellOrderRepositoryTest {
    private User seller;

    @Autowired
    private SellOrderRepository sellOrderRepository;
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
    void setTestData() {
        seller = User.builder()
                .username("sellerUser")
                .email("seller@example.com")
                .password("securePassword")
                .lastLogin(LocalDateTime.now())
                .build();

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .description("A decentralized digital currency")
                .build();

        userRepository.save(seller);
        virtualAssetRepository.save(virtualAsset);

        List<SellOrder> sellOrders = Arrays.asList(
                createSellOrder(5L, 50L, OrderStatus.PENDING, seller, virtualAsset),
                createSellOrder(10L, 100L, OrderStatus.COMPLETED, seller, virtualAsset),
                createSellOrder(15L, 150L, OrderStatus.PENDING, seller, virtualAsset),
                createSellOrder(20L, 200L, OrderStatus.PENDING, seller, virtualAsset)
        );
        sellOrderRepository.saveAll(sellOrders);
    }

    @AfterEach
    void clearTestData() {
        sellOrderRepository.deleteAll();
        userRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    SellOrder createSellOrder(Long quantity, Long price, OrderStatus status, User seller, VirtualAsset virtualAsset) {
        return SellOrder.builder()
                .seller(seller)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(quantity))
                .price(BigDecimal.valueOf(price))
                .status(status)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testFindBySellerUserIdAndStatus() {
        Optional<List<SellOrder>> result = sellOrderRepository.findBySeller_IdAndStatus(
                seller.getId(),
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            result.ifPresent(sellOrders -> {
                softly.assertThat(sellOrders).hasSize(3);
                sellOrders.forEach(
                        sellOrder -> softly.assertThat(sellOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
            });
        });
    }

    @Test
    void testFindMatchableSellOrders() {
        Optional<List<SellOrder>> result = sellOrderRepository.findMatchableSellOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            result.ifPresent(sellOrders -> {
                softly.assertThat(sellOrders).hasSize(1);
                softly.assertThat(sellOrders.getFirst().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
            });
        });
    }
}