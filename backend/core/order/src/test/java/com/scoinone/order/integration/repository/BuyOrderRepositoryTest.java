package com.scoinone.order.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.repository.BuyOrderRepository;
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
class BuyOrderRepositoryTest {
    private static final String testBuyerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaabuyer";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    private BuyOrderEntity buyOrder;

    @Autowired
    private BuyOrderRepository buyOrderRepository;

    @BeforeEach
    void setUp() {
        List<BuyOrderEntity> buyOrders = Arrays.asList(
                createBuyOrder(5L, 50L, OrderStatus.PENDING, testBuyerId, testVirtualAssetId),
                createBuyOrder(10L, 100L, OrderStatus.COMPLETED, testBuyerId, testVirtualAssetId),
                createBuyOrder(15L, 150L, OrderStatus.PENDING, testBuyerId, testVirtualAssetId),
                createBuyOrder(20L, 200L, OrderStatus.PENDING, testBuyerId, testVirtualAssetId)
        );
        buyOrderRepository.saveAll(buyOrders);

        buyOrder = createBuyOrder(1L, 10L, OrderStatus.PENDING, testBuyerId, testVirtualAssetId);
        buyOrderRepository.save(buyOrder);
    }

    @AfterEach
    void tearDown() {
        buyOrderRepository.deleteAll();
    }

    BuyOrderEntity createBuyOrder(Long quantity, Long price, OrderStatus status, String buyerId, String virtualAssetId) {
        return BuyOrderEntity.builder()
                .buyerId(buyerId)
                .virtualAssetId(virtualAssetId)
                .quantity(BigDecimal.valueOf(quantity))
                .price(BigDecimal.valueOf(price))
                .status(status)
                .tradeTime(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("구매자 ID와 구매 주문 상태로 구매 주문 조회")
    void testFindByBuyerUserIdAndStatus() {
        List<BuyOrderEntity> result = buyOrderRepository.findByBuyerIdAndStatus(
                testBuyerId,
                OrderStatus.PENDING
        );

        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(4);
            result.forEach(buyOrder -> softly.assertThat(buyOrder.getStatus()).isEqualTo(OrderStatus.PENDING));
        });
    }

    @Test
    @DisplayName("판매가에 따른 구매 가능 주문 조회")
    void testFindMatchableBuyOrders() {
        List<BuyOrderEntity> result = buyOrderRepository.findMatchableBuyOrders(BigDecimal.valueOf(100.00));

        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(2);
            softly.assertThat(result.get(0).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(150.00));
            softly.assertThat(result.get(1).getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200.00));
        });
    }

    @Test
    @DisplayName("판매 주문 ID 및 판매자 ID에 따른 주문 삭제")
    void testDeleteByOrderIdAndBuyerId() {
        Long count = buyOrderRepository.deleteByIdAndBuyerId(buyOrder.getId(), testBuyerId);

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(1);
        });
    }
}