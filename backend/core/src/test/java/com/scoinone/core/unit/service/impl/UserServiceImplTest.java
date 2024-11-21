package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.BuyOrderRepository;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.repository.OwnedVirtualAssetRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.TradeRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OwnedVirtualAssetRepository ownedVirtualAssetRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private BuyOrderRepository buyOrderRepository;

    @Mock
    private SellOrderRepository sellOrderRepository;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Clock clock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    public void testGetUserById_Success() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findById(userId);
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트 실패")
    public void testGetUserById_NotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.getUserById(userId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User not found with id: " + userId);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트")
    public void testCreateUser_Success() {
        User user = User.builder()
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getEmail()).isEqualTo("test@example.com");
            verify(userRepository).save(user);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트 실패 - 이미 가입한 사용자")
    public void testCreateUser_UserAlreadyExists() {
        User user = User.builder()
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.createUser(user))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("User is already Existed!");
        });
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    public void testUpdateUser_Success() {
        Long userId = 1L;
        User existingUser = User.builder()
                .id(userId)
                .username("OldUsername")
                .build();
        User updatedUser = User.builder()
                .username("NewUsername")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User result = userService.updateUser(userId, updatedUser);

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(existingUser);
            softly.assertThat(existingUser.getUsername()).isEqualTo("NewUsername");
            verify(userRepository).findById(userId);
        });
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    @DisplayName("사용자 닉네임(이메일)으로 사용자 조회 테스트 - spring security")
    public void testLoadUserByUsername_Success() {
        String email = "test@example.com";
        User user = User.builder().build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(email);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findByEmail(email);
        });
    }

    @Test
    @DisplayName("사용자 닉네임(이메일)으로 사용자 조회 테스트 실패 - spring security")
    public void testLoadUserByUsername_NotFound() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.loadUserByUsername(email))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User not found with email: " + email);
        });
    }

    @Test
    @DisplayName("사용자 알림 30일까지 조회")
    public void testGetCommentsFromLast30DaysByUserId() {
        when(clock.instant()).thenReturn(Instant.parse("2024-11-21T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long userId = 1L;
        LocalDateTime thirtyDaysAgo = LocalDateTime.now(clock).minusDays(30);
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findByUser_UserIdAndCreatedAtAfter(userId, thirtyDaysAgo))
                .thenReturn(Optional.of(notifications));

        List<Notification> result = userService.getNotificationsFromLast30DaysByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(notificationRepository).findByUser_UserIdAndCreatedAtAfter(userId, thirtyDaysAgo);
        });
    }

    @Test
    @DisplayName("사용자 보유 가상자산 조회")
    public void testGetOwnedVirtualAssetsByUserId() {
        Long userId = 1L;
        List<OwnedVirtualAsset> ownedAssets = new ArrayList<>();
        when(ownedVirtualAssetRepository.findByUser_UserId(userId)).thenReturn(Optional.of(ownedAssets));

        List<OwnedVirtualAsset> result = userService.getOwnedVirtualAssetsByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(ownedVirtualAssetRepository).findByUser_UserId(userId);
        });
    }

    @Test
    @DisplayName("사용자 구매 주문 조회")
    public void testGetBuyOrderByUserId() {
        Long userId = 1L;
        List<BuyOrder> buyOrders = new ArrayList<>();
        when(buyOrderRepository.findByBuyer_UserIdAndStatus(userId, OrderStatus.PENDING))
                .thenReturn(Optional.of(buyOrders));

        List<BuyOrder> result = userService.getBuyOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(buyOrderRepository).findByBuyer_UserIdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 판매 주문 조회")
    public void testGetSellOrderByUserId() {
        Long userId = 1L;
        List<SellOrder> sellOrders = new ArrayList<>();
        when(sellOrderRepository.findBySeller_UserIdAndStatus(userId, OrderStatus.PENDING))
                .thenReturn(Optional.of(sellOrders));

        List<SellOrder> result = userService.getSellOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(sellOrderRepository).findBySeller_UserIdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 전체 주문 조회")
    public void testGetOrderByUserId() {
        Long userId = 1L;
        List<BuyOrder> buyOrders = new ArrayList<>();
        List<SellOrder> sellOrders = new ArrayList<>();
        when(buyOrderRepository.findByBuyer_UserIdAndStatus(userId, OrderStatus.PENDING)).thenReturn(
                Optional.of(buyOrders));
        when(sellOrderRepository.findBySeller_UserIdAndStatus(userId, OrderStatus.PENDING)).thenReturn(
                Optional.of(sellOrders));

        List<Object> result = userService.getOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(buyOrders.size() + sellOrders.size());
            verify(buyOrderRepository).findByBuyer_UserIdAndStatus(userId, OrderStatus.PENDING);
            verify(sellOrderRepository).findBySeller_UserIdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 체결 거래 조회")
    public void testGetTradeByUserId() {
        Long userId = 1L;
        List<Trade> buyingTrades = new ArrayList<>();
        List<Trade> sellingTrades = new ArrayList<>();
        when(tradeRepository.findByBuyOrder_Buyer_UserId(userId)).thenReturn(Optional.of(buyingTrades));
        when(tradeRepository.findBySellOrder_Seller_UserId(userId)).thenReturn(Optional.of(sellingTrades));

        List<Trade> result = userService.getTradeByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(buyingTrades.size() + sellingTrades.size());
            verify(tradeRepository).findByBuyOrder_Buyer_UserId(userId);
            verify(tradeRepository).findBySellOrder_Seller_UserId(userId);
        });
    }

    @Test
    @DisplayName("사용자 질의 게시글 조회")
    public void testGetQuestionsByUserId() {
        Long userId = 1L;
        List<Post> questions = new ArrayList<>();
        when(postRepository.findByUser_UserIdAndPostType(userId, PostType.QNA)).thenReturn(Optional.of(questions));

        List<Post> result = userService.getQuestionsByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(questions.size());
            verify(postRepository).findByUser_UserIdAndPostType(userId, PostType.QNA);
        });
    }
}