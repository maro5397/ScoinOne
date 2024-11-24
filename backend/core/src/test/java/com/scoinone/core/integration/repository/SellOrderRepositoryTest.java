package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.config.TestContainerConfig;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = CoreApplication.class)
@Import(TestContainerConfig.class)
class SellOrderRepositoryTest {
    private User seller;

    @Autowired
    private SellOrderRepository sellOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VirtualAssetRepository virtualAssetRepository;

    @BeforeEach
    void setUp() {
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
    void tearDown() {
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
    @DisplayName("판매자 ID와 판매 주문 상태로 판매 주문 조회")
    void testFindBySellerUserIdAndStatus() {
        List<SellOrder> sellOrders = sellOrderRepository.findBySeller_IdAndStatus(
                seller.getId(),
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(sellOrders).hasSize(3);
            sellOrders.forEach(sellOrder -> softly.assertThat(sellOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
        });
    }

    @Test
    @DisplayName("구매가에 따른 판매 가능 주문 조회")
    void testFindMatchableSellOrders() {
        List<SellOrder> sellOrders = sellOrderRepository.findMatchableSellOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(sellOrders).hasSize(1);
            softly.assertThat(sellOrders.getFirst().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
        });
    }
}