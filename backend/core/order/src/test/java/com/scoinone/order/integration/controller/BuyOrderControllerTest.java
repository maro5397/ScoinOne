package com.scoinone.order.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.order.config.TestContainerConfig;
import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.request.order.CreateBuyOrderRequestDto;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.service.BuyOrderService;
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
class BuyOrderControllerTest {
    private static final String testBuyerId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaabuyer";
    private static final String testVirtualAssetId = "bbbbbbbb-bbbb-bbbb-bbbb-virtualasset";

    @Autowired
    private TestRestTemplate restTemplate;

    private BuyOrderEntity savedBuyOrder;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(@Autowired BuyOrderService buyOrderService) {
        savedBuyOrder = buyOrderService.createBuyOrder(
                testVirtualAssetId,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(50.0),
                testBuyerId
        );
        headers = new HttpHeaders();
        headers.set("UserId", testBuyerId);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired BuyOrderRepository buyOrderRepository) {
        buyOrderRepository.deleteAll();
    }

    @Test
    @DisplayName("구매 주문 생성 테스트")
    void createBuyOrder_shouldReturnCreatedOrder() {
        CreateBuyOrderRequestDto requestDto = new CreateBuyOrderRequestDto();
        requestDto.setQuantity(BigDecimal.valueOf(10));
        requestDto.setPrice(BigDecimal.valueOf(100.0));

        ResponseEntity<CreateBuyOrderResponseDto> response = restTemplate.exchange(
                "/api/buy/" + testVirtualAssetId,
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateBuyOrderResponseDto.class
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
    @DisplayName("구매 주문 삭제 테스트")
    void deleteBuyOrder_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/buy/" + savedBuyOrder.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("BuyOrder deleted successfully");
        });
    }

    @Test
    @DisplayName("구매 주문 리스트 조회 테스트")
    void getBuyOrders_shouldReturnListOfOrders() {
        ResponseEntity<GetBuyOrdersResponseDto> response = restTemplate.exchange(
                "/api/buy",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetBuyOrdersResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getBuyOrders()).hasSize(1);
        });
    }
}