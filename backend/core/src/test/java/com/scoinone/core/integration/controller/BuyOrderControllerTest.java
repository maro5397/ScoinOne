package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.order.CreateBuyOrderRequestDto;
import com.scoinone.core.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.BuyOrderService;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BuyOrderControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VirtualAssetRepository virtualAssetRepository;

    @Autowired
    private BuyOrderRepository buyOrderRepository;

    @Autowired
    private UserRepository userRepository;

    private VirtualAsset savedVirtualAsset;

    private BuyOrder savedBuyOrder;

    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired VirtualAssetService virtualAssetService,
            @Autowired UserService userService,
            @Autowired BuyOrderService buyOrderService,
            @Autowired AuthService authService
    ) {
        savedVirtualAsset = virtualAssetService.createVirtualAsset("Bitcoin", "BTC", "Digital currency");

        User user = userService.createUser("test@example.com", "securePassword", "testUser");

        savedBuyOrder = buyOrderService.createBuyOrder(
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
    void tearDown() {
        buyOrderRepository.deleteAll();
        userRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("구매 주문 생성 테스트")
    void createBuyOrder_shouldReturnCreatedOrder() {
        CreateBuyOrderRequestDto requestDto = new CreateBuyOrderRequestDto();
        requestDto.setQuantity(BigDecimal.valueOf(10));
        requestDto.setPrice(BigDecimal.valueOf(100.0));

        ResponseEntity<CreateBuyOrderResponseDto> response = restTemplate.exchange(
                "/api/buy/" + savedVirtualAsset.getId().toString(),
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