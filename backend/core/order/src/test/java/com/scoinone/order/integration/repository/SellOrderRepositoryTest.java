package com.scoinone.order.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.repository.SellOrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
class SellOrderRepositoryTest {
    private static final String testSellerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaseller";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    private SellOrderEntity sellOrder;

    @Autowired
    private SellOrderRepository sellOrderRepository;

    @BeforeEach
    void setUp() {
        List<SellOrderEntity> sellOrders = Arrays.asList(
                createSellOrder(5L, 50L, OrderStatus.PENDING, testSellerId, testVirtualAssetId),
                createSellOrder(10L, 100L, OrderStatus.COMPLETED, testSellerId, testVirtualAssetId),
                createSellOrder(15L, 150L, OrderStatus.PENDING, testSellerId, testVirtualAssetId),
                createSellOrder(20L, 200L, OrderStatus.PENDING, testSellerId, testVirtualAssetId)
        );
        sellOrderRepository.saveAll(sellOrders);

        sellOrder = createSellOrder(25L, 250L, OrderStatus.PENDING, testSellerId, testVirtualAssetId);
        sellOrderRepository.save(sellOrder);
    }

    @AfterEach
    void tearDown() {
        sellOrderRepository.deleteAll();
    }

    SellOrderEntity createSellOrder(Long quantity, Long price, OrderStatus status, String sellerId, String virtualAssetId) {
        return SellOrderEntity.builder()
                .sellerId(sellerId)
                .virtualAssetId(virtualAssetId)
                .quantity(BigDecimal.valueOf(quantity))
                .price(BigDecimal.valueOf(price))
                .status(status)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("판매자 ID와 판매 주문 상태로 판매 주문 조회")
    void testFindBySellerUserIdAndStatus() {
        List<SellOrderEntity> sellOrders = sellOrderRepository.findBySellerIdAndStatus(
                testSellerId,
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(sellOrders).hasSize(4);
            sellOrders.forEach(sellOrder -> softly.assertThat(sellOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
        });
    }

    @Test
    @DisplayName("구매가에 따른 판매 가능 주문 조회")
    void testFindMatchableSellOrders() {
        List<SellOrderEntity> sellOrders = sellOrderRepository.findMatchableSellOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(sellOrders).hasSize(1);
            softly.assertThat(sellOrders.getFirst().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
        });
    }
}