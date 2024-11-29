package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.order.CreateSellOrderRequestDto;
import com.scoinone.core.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.SellOrderService;
import com.scoinone.core.service.UserService;
import com.scoinone.core.service.VirtualAssetService;
import java.math.BigDecimal;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    @Autowired
    private TestRestTemplate restTemplate;

    private VirtualAsset savedVirtualAsset;

    private SellOrder savedSellOrder;

    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired VirtualAssetService virtualAssetService,
            @Autowired UserService userService,
            @Autowired SellOrderService sellOrderService,
            @Autowired AuthService authService
    ) {
        savedVirtualAsset = virtualAssetService.createVirtualAsset("Bitcoin", "BTC", "Digital currency");

        User user = userService.createUser("test@example.com", "securePassword", "testUser");

        savedSellOrder = sellOrderService.createSellOrder(
                savedVirtualAsset.getId(),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(50.0),
                user
        );

        String jwtToken = authService.authenticate("test@example.com", "securePassword");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(
            @Autowired VirtualAssetRepository virtualAssetRepository,
            @Autowired SellOrderRepository sellOrderRepository,
            @Autowired UserRepository userRepository
    ) {
        sellOrderRepository.deleteAll();
        userRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("판매 주문 생성 테스트")
    void createSellOrder_shouldReturnCreatedOrder() {
        CreateSellOrderRequestDto requestDto = new CreateSellOrderRequestDto();
        requestDto.setQuantity(BigDecimal.valueOf(10));
        requestDto.setPrice(BigDecimal.valueOf(100.0));

        ResponseEntity<CreateSellOrderResponseDto> response = restTemplate.exchange(
                "/api/sell/" + savedVirtualAsset.getId().toString(),
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