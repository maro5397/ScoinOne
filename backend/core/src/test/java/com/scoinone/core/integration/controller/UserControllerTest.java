package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.common.PostType;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.user.CreateUserRequestDto;
import com.scoinone.core.dto.request.user.UpdateUserRequestDto;
import com.scoinone.core.dto.response.notification.GetNotificationsResponseDto;
import com.scoinone.core.dto.response.post.GetPostsResponseDto;
import com.scoinone.core.dto.response.trade.GetTradesResponseDto;
import com.scoinone.core.dto.response.user.CreateUserResponseDto;
import com.scoinone.core.dto.response.user.GetOwnedAssetsResponseDto;
import com.scoinone.core.dto.response.user.GetUserResponseDto;
import com.scoinone.core.dto.response.user.UpdateUserResponseDto;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.repository.OwnedVirtualAssetRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.BuyOrderService;
import com.scoinone.core.service.NotificationService;
import com.scoinone.core.service.PostService;
import com.scoinone.core.service.SellOrderService;
import com.scoinone.core.service.UserService;
import com.scoinone.core.service.VirtualAssetService;
import com.scoinone.core.util.UserDataInitializer;
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
@Import({TestContainerConfig.class, UserDataInitializer.class})
@ActiveProfiles("dev")
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostService postService;
    @Autowired
    private BuyOrderService buyOrderService;
    @Autowired
    private SellOrderService sellOrderService;
    @Autowired
    private VirtualAssetService virtualAssetService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    private User savedUser;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        savedUser = userService.getUserByEmail("user@example.com");
        String jwtToken = authService.authenticate("user@example.com", "password");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(
            @Autowired NotificationRepository notificationRepository,
            @Autowired TradeRepository tradeRepository,
            @Autowired BuyOrderRepository buyOrderRepository,
            @Autowired SellOrderRepository sellOrderRepository,
            @Autowired VirtualAssetRepository virtualAssetRepository,
            @Autowired PostRepository postRepository,
            @Autowired OwnedVirtualAssetRepository ownedVirtualAssetRepository
    ) {
        notificationRepository.deleteAll();
        postRepository.deleteAll();
        tradeRepository.deleteAll();
        buyOrderRepository.deleteAll();
        sellOrderRepository.deleteAll();
        ownedVirtualAssetRepository.deleteAll();
        virtualAssetRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    void createUser_shouldReturnCreatedUser() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("newUser@example.com");
        requestDto.setPassword("newPassword");
        requestDto.setUsername("newUser");

        ResponseEntity<CreateUserResponseDto> response = restTemplate.exchange(
                "/api/user/signup",
                HttpMethod.POST,
                new HttpEntity<>(requestDto),
                CreateUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getEmail()).isEqualTo("newUser@example.com");
            softly.assertThat(response.getBody().getUsername()).isEqualTo("newUser");
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    void getUser_shouldReturnUser() {
        ResponseEntity<GetUserResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getEmail()).isEqualTo(savedUser.getEmail());
            softly.assertThat(response.getBody().getUsername()).isEqualTo(savedUser.getCustomUsername());
        });
    }

    @Test
    @DisplayName("사용자 수정 테스트")
    void updateUser_shouldReturnUpdatedUser() {
        UpdateUserRequestDto updateRequestDto = new UpdateUserRequestDto();
        updateRequestDto.setUsername("updatedUser");

        ResponseEntity<UpdateUserResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo("updatedUser");
        });
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteUser_shouldReturnOk() {
        userService.createUser("secondUser@example.com", "password", "secondUser");
        String jwtToken = authService.authenticate("secondUser@example.com", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");

        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("User deleted successfully");
        });
    }

    @Test
    @DisplayName("사용자 알림 조회 테스트")
    void getNotifications_shouldReturnListOfNotifications() {
        notificationService.createNotification(savedUser.getEmail(), "Test Notification1");
        notificationService.createNotification(savedUser.getEmail(), "Test Notification2");
        notificationService.createNotification(savedUser.getEmail(), "Test Notification3");

        ResponseEntity<GetNotificationsResponseDto> response = restTemplate.exchange(
                "/api/user/notification",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetNotificationsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getNotifications()).hasSize(3);
        });
    }

    @Test
    @DisplayName("사용자 거래 조회 테스트")
    void getTrades_shouldReturnListOfTrades() {
        VirtualAsset virtualAsset = virtualAssetService.createVirtualAsset("Bitcoin", "BTC", "Digital currency");
        buyOrderService.createBuyOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5000),
                savedUser
        );
        sellOrderService.createSellOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5000),
                savedUser
        );
        sellOrderService.createSellOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(5000),
                savedUser
        );
        sellOrderService.createSellOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5000),
                savedUser
        );

        ResponseEntity<GetTradesResponseDto> response = restTemplate.exchange(
                "/api/user/trade",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetTradesResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getTrades()).hasSize(2);
        });
    }

    @Test
    @DisplayName("사용자 질문 게시물 조회 테스트")
    void getQuestionPosts_shouldReturnListOfPosts() {
        postService.createPost("testTitle1", "testContent1", savedUser, PostType.QNA);
        postService.createPost("testTitle2", "testContent2", savedUser, PostType.QNA);

        ResponseEntity<GetPostsResponseDto> response = restTemplate.exchange(
                "/api/user/question",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetPostsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getPosts()).hasSize(2);
        });
    }

    @Test
    @DisplayName("사용자 보유 자산 조회 테스트")
    void getVirtualAssets_shouldReturnListOfOwnedAssets() {
        VirtualAsset virtualAsset = virtualAssetService.createVirtualAsset("Bitcoin", "BTC", "Digital currency");
        buyOrderService.createBuyOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(5000),
                savedUser
        );
        sellOrderService.createSellOrder(
                virtualAsset.getId(),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5000),
                savedUser
        );

        ResponseEntity<GetOwnedAssetsResponseDto> response = restTemplate.exchange(
                "/api/user/asset",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetOwnedAssetsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getOwnedAssets()).hasSize(1);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getOwnedAssets().getFirst().getAmount())
                    .isEqualByComparingTo(BigDecimal.ZERO);
            softly.assertThat(
                    Objects.requireNonNull(response.getBody())
                            .getOwnedAssets()
                            .getFirst()
                            .getVirtualAssetId()).isEqualTo(virtualAsset.getId());
        });
    }
}