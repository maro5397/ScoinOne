package com.scoinone.order.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.TradeEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.repository.TradeRepository;
import java.math.BigDecimal;
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
class TradeRepositoryTest {
    private static final String testBuyerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaabuyer";
    private static final String testSellerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaseller";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BuyOrderRepository buyOrderRepository;

    @Autowired
    private SellOrderRepository sellOrderRepository;

    @BeforeEach
    void setUp() {
        BuyOrderEntity buyOrder = BuyOrderEntity.builder()
                .buyerId(testBuyerId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(50000))
                .status(OrderStatus.COMPLETED)
                .build();
        buyOrderRepository.save(buyOrder);

        SellOrderEntity sellOrder = SellOrderEntity.builder()
                .sellerId(testSellerId)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(50000))
                .status(OrderStatus.COMPLETED)
                .build();
        sellOrderRepository.save(sellOrder);

        TradeEntity trade = TradeEntity.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAssetId(testVirtualAssetId)
                .quantity(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(50000))
                .build();
        tradeRepository.save(trade);
    }

    @AfterEach
    void tearDown() {
        tradeRepository.deleteAll();
        buyOrderRepository.deleteAll();
        sellOrderRepository.deleteAll();
    }

    @Test
    @DisplayName("구매 주문의 구매자 ID로 체결된 거래 조회")
    void testFindByBuyOrder_Buyer_Id() {
        List<TradeEntity> trades = tradeRepository.findByBuyOrder_BuyerId(testBuyerId);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(1);
            softly.assertThat(trades.getFirst().getBuyOrder().getBuyerId()).isEqualTo(testBuyerId);
        });
    }

    @Test
    @DisplayName("판매 주문의 판매자 ID로 체결된 거래 조회")
    void testFindBySellOrder_Seller_Id() {
        List<TradeEntity> trades = tradeRepository.findBySellOrder_SellerId(testSellerId);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(1);
            softly.assertThat(trades.getFirst().getSellOrder().getSellerId()).isEqualTo(testSellerId);
        });
    }
}