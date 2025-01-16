package com.scoinone.order.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.request.order.CreateSellOrderRequestDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.repository.SellOrderRepository;
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
class SellOrderControllerTest {
    private static final String testSellerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaseller";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    @Autowired
    private TestRestTemplate restTemplate;

    private SellOrderEntity savedSellOrder;

    private HttpHeaders headers;

    @BeforeEach
    void setUp(@Autowired SellOrderService sellOrderService) {
        savedSellOrder = sellOrderService.createSellOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(50.0),
                testSellerId
        );
        headers = new HttpHeaders();
        headers.set("UserId", testSellerId);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired SellOrderRepository sellOrderRepository) {
        sellOrderRepository.deleteAll();
    }

    @Test
    @DisplayName("판매 주문 생성 테스트")
    void createSellOrder_shouldReturnCreatedOrder() {
        CreateSellOrderRequestDto requestDto = new CreateSellOrderRequestDto();
        requestDto.setQuantity(BigDecimal.valueOf(10));
        requestDto.setPrice(BigDecimal.valueOf(100.0));

        ResponseEntity<CreateSellOrderResponseDto> response = restTemplate.exchange(
                "/api/sell/" + testVirtualAssetId,
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateSellOrderResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getQuantity())
                    .isEqualByComparingTo(BigDecimal.valueOf(10));
            softly.assertThat(Objects.requireNonNull(response.getBody()).getPrice())
                    .isEqualByComparingTo(BigDecimal.valueOf(100.0));
        });
    }

    @Test
    @DisplayName("판매 주문 삭제 테스트")
    void deleteSellOrder_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/sell/" + savedSellOrder.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("SellOrder deleted successfully");
        });
    }

    @Test
    @DisplayName("판매 주문 리스트 조회 테스트")
    void getSellOrders_shouldReturnListOfOrders() {
        ResponseEntity<GetSellOrdersResponseDto> response = restTemplate.exchange(
                "/api/sell",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetSellOrdersResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getSellOrders()).hasSize(1);
        });
    }
}