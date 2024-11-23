package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
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
class BuyOrderRepositoryTest {
    private User buyer;

    @Autowired
    private BuyOrderRepository buyOrderRepository;
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
        buyer = User.builder()
                .username("buyerUser")
                .email("buyer@example.com")
                .password("securePassword")
                .lastLogin(LocalDateTime.now())
                .build();

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .description("A decentralized digital currency")
                .build();

        userRepository.save(buyer);
        virtualAssetRepository.save(virtualAsset);

        List<BuyOrder> buyOrders = Arrays.asList(
                createBuyOrder(5L, 50L, OrderStatus.PENDING, buyer, virtualAsset),
                createBuyOrder(10L, 100L, OrderStatus.COMPLETED, buyer, virtualAsset),
                createBuyOrder(15L, 150L, OrderStatus.PENDING, buyer, virtualAsset),
                createBuyOrder(20L, 200L, OrderStatus.PENDING, buyer, virtualAsset)
        );
        buyOrderRepository.saveAll(buyOrders);
    }

    @AfterEach
    void clearTestData() {
        buyOrderRepository.deleteAll();
        userRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    BuyOrder createBuyOrder(Long quantity, Long price, OrderStatus status, User buyer, VirtualAsset virtualAsset) {
        return BuyOrder.builder()
                .buyer(buyer)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(quantity))
                .price(BigDecimal.valueOf(price))
                .status(status)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testFindByBuyerUserIdAndStatus() {
        Optional<List<BuyOrder>> result = buyOrderRepository.findByBuyer_IdAndStatus(
                buyer.getId(),
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            result.ifPresent(buyOrders -> {
                softly.assertThat(buyOrders).hasSize(3);
                buyOrders.forEach(buyOrder -> softly.assertThat(buyOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
            });
        });
    }

    @Test
    void testFindMatchableBuyOrders() {
        Optional<List<BuyOrder>> result = buyOrderRepository.findMatchableBuyOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            result.ifPresent(buyOrders -> {
                softly.assertThat(buyOrders).hasSize(2);
                softly.assertThat(buyOrders.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(150.00));
                softly.assertThat(buyOrders.get(1).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200.00));
            });
        });
    }
}