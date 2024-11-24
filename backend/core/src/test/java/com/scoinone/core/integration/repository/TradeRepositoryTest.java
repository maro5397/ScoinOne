package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
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
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = CoreApplication.class)
@Import(TestContainerConfig.class)
class TradeRepositoryTest {
    private User buyer;
    private User seller;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BuyOrderRepository buyOrderRepository;

    @Autowired
    private SellOrderRepository sellOrderRepository;

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
                .build();
        userRepository.save(buyer);

        seller = User.builder()
                .username("sellerUser")
                .email("seller@example.com")
                .password("securePassword")
                .build();
        userRepository.save(seller);

        VirtualAsset virtualAsset = VirtualAsset.builder()
                .name("Bitcoin")
                .symbol("BTC")
                .description("A decentralized digital currency")
                .build();
        virtualAssetRepository.save(virtualAsset);

        BuyOrder buyOrder = BuyOrder.builder()
                .buyer(buyer)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(50000))
                .status(OrderStatus.COMPLETED)
                .build();
        buyOrderRepository.save(buyOrder);

        SellOrder sellOrder = SellOrder.builder()
                .seller(seller)
                .virtualAsset(virtualAsset)
                .quantity(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(50000))
                .status(OrderStatus.COMPLETED)
                .build();
        sellOrderRepository.save(sellOrder);

        Trade trade = Trade.builder()
                .buyOrder(buyOrder)
                .sellOrder(sellOrder)
                .virtualAsset(virtualAsset)
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
        userRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("구매 주문의 구매자 ID로 체결된 거래 조회")
    void testFindByBuyOrder_Buyer_Id() {
        Long buyerId = buyer.getId();

        List<Trade> trades = tradeRepository.findByBuyOrder_Buyer_Id(buyerId);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(1);
            softly.assertThat(trades.getFirst().getBuyOrder().getBuyer().getId()).isEqualTo(buyerId);
        });
    }

    @Test
    @DisplayName("판매 주문의 판매자 ID로 체결된 거래 조회")
    void testFindBySellOrder_Seller_Id() {
        Long sellerId = seller.getId();

        List<Trade> trades = tradeRepository.findBySellOrder_Seller_Id(sellerId);

        assertSoftly(softly -> {
            softly.assertThat(trades).hasSize(1);
            softly.assertThat(trades.getFirst().getSellOrder().getSeller().getId()).isEqualTo(sellerId);
        });
    }
}