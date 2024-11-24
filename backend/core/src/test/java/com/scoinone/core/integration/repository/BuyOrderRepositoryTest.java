package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.config.TestContainerConfig;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = CoreApplication.class)
@Import(TestContainerConfig.class)
class BuyOrderRepositoryTest {
    private User buyer;

    @Autowired
    private BuyOrderRepository buyOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VirtualAssetRepository virtualAssetRepository;

    @BeforeEach
    void setUp() {
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
    void tearDown() {
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
    @DisplayName("구매자 ID와 구매 주문 상태로 구매 주문 조회")
    void testFindByBuyerUserIdAndStatus() {
        List<BuyOrder> result = buyOrderRepository.findByBuyer_IdAndStatus(
                buyer.getId(),
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(3);
            result.forEach(buyOrder -> softly.assertThat(buyOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
        });
    }

    @Test
    @DisplayName("판매가에 따른 구매 가능 주문 조회")
    void testFindMatchableBuyOrders() {
        List<BuyOrder> result = buyOrderRepository.findMatchableBuyOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(2);
            softly.assertThat(result.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(150.00));
            softly.assertThat(result.get(1).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200.00));
        });
    }
}