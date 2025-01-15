package com.scoinone.order.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.dto.response.trade.GetTradesResponseDto;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.repository.TradeRepository;
import com.scoinone.order.service.BuyOrderService;
import com.scoinone.order.service.SellOrderService;
import java.math.BigDecimal;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class})
@ActiveProfiles("dev")
class TradeControllerTest {
    private static final String testBuyerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaabuyer";
    private static final String testSellerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaseller";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired SellOrderService sellOrderService,
            @Autowired BuyOrderService buyOrderService
    ) {
        buyOrderService.createBuyOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5000),
                testBuyerId
        );
        sellOrderService.createSellOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5000),
                testSellerId
        );
        sellOrderService.createSellOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(5000),
                testSellerId
        );
        sellOrderService.createSellOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5000),
                testSellerId
        );

        headers = new HttpHeaders();
        headers.set("UserId", testBuyerId);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(
            @Autowired SellOrderRepository sellOrderRepository,
            @Autowired BuyOrderRepository buyOrderRepository,
            @Autowired TradeRepository tradeRepository
    ) {
        tradeRepository.deleteAll();
        sellOrderRepository.deleteAll();
        buyOrderRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 거래 조회 테스트")
    void getTrades_shouldReturnListOfTrades() {
        ResponseEntity<GetTradesResponseDto> response = restTemplate.exchange(
                "/api/trade",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetTradesResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getTrades()).hasSize(2);
        });
    }
}