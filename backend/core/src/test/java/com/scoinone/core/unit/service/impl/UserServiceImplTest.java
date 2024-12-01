package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.common.status.OrderStatus;
import com.scoinone.core.common.type.PostType;
import com.scoinone.core.entity.Authority;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.AuthorityRepository;
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
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    private AuthorityRepository authorityRepository;

    @Mock
    private Clock clock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clock.instant()).thenReturn(Instant.parse("2024-11-21T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    public void testGetUserById_Success() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@example.com");

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findByEmail(user.getEmail());
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트 실패")
    public void testGetUserById_NotFound() {
        String userEmail = "test@example.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.getUserByEmail(userEmail))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User not found with email: " + userEmail);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트")
    public void testCreateUser_Success() {
        String email = "test@example.com";
        String password = "securePassword";
        String username = "testUser";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(authorityRepository.findByAuthorityName("ROLE_USER"))
                .thenReturn(Optional.of(Authority.builder().authorityName("ROLE_USER").build()));

        userService.createUser(email, password, username);

        ArgumentCaptor<User> userCaptor = forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertSoftly(softly -> {
            softly.assertThat(savedUser).isNotNull();
            softly.assertThat(savedUser.getEmail()).isEqualTo(email);
            softly.assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
            softly.assertThat(savedUser.getCustomUsername()).isEqualTo(username);
            softly.assertThat(savedUser.getAuthorities()).hasSize(1);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트 실패 - 이미 가입한 사용자")
    public void testCreateUser_UserAlreadyExists() {
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("securePassword")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.createUser(
                            user.getEmail(),
                            user.getPassword(),
                            user.getCustomUsername()
                    ))
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

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User result = userService.updateUser(userId, "NewUsername");

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(existingUser);
            softly.assertThat(existingUser.getCustomUsername()).isEqualTo("NewUsername");
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
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("User not found with email: " + email);
        });
    }

    @Test
    @DisplayName("사용자 알림 30일까지 조회")
    public void testGetCommentsFromLast30DaysByUserId() {
        Long userId = 1L;
        List<Notification> notifications = Collections.singletonList(Notification.builder().build());
        when(notificationRepository.findByUserIdAndLast30Days(userId)).thenReturn(notifications);

        List<Notification> result = userService.getNotificationsFromLast30DaysByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(notificationRepository).findByUserIdAndLast30Days(userId);
        });
    }

    @Test
    @DisplayName("사용자 보유 가상자산 조회")
    public void testGetOwnedVirtualAssetsByUserId() {
        Long userId = 1L;
        List<OwnedVirtualAsset> ownedAssets = Collections.singletonList(OwnedVirtualAsset.builder().build());
        when(ownedVirtualAssetRepository.findByUser_Id(userId)).thenReturn(ownedAssets);

        List<OwnedVirtualAsset> result = userService.getOwnedVirtualAssetsByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(ownedVirtualAssetRepository).findByUser_Id(userId);
        });
    }

    @Test
    @DisplayName("사용자 구매 주문 조회")
    public void testGetBuyOrderByUserId() {
        Long userId = 1L;
        List<BuyOrder> buyOrders = Collections.singletonList(BuyOrder.builder().build());
        when(buyOrderRepository.findByBuyer_IdAndStatus(userId, OrderStatus.PENDING)).thenReturn(buyOrders);

        List<BuyOrder> result = userService.getBuyOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(buyOrderRepository).findByBuyer_IdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 판매 주문 조회")
    public void testGetSellOrderByUserId() {
        Long userId = 1L;
        List<SellOrder> sellOrders = Collections.singletonList(SellOrder.builder().build());
        when(sellOrderRepository.findBySeller_IdAndStatus(userId, OrderStatus.PENDING)).thenReturn(sellOrders);

        List<SellOrder> result = userService.getSellOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(sellOrderRepository).findBySeller_IdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 전체 주문 조회")
    public void testGetOrderByUserId() {
        Long userId = 1L;
        List<BuyOrder> buyOrders = Collections.singletonList(BuyOrder.builder().build());
        List<SellOrder> sellOrders = Collections.singletonList(SellOrder.builder().build());
        when(buyOrderRepository.findByBuyer_IdAndStatus(userId, OrderStatus.PENDING)).thenReturn(buyOrders);
        when(sellOrderRepository.findBySeller_IdAndStatus(userId, OrderStatus.PENDING)).thenReturn(sellOrders);

        List<Object> result = userService.getOrderByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(buyOrders.size() + sellOrders.size());
            verify(buyOrderRepository).findByBuyer_IdAndStatus(userId, OrderStatus.PENDING);
            verify(sellOrderRepository).findBySeller_IdAndStatus(userId, OrderStatus.PENDING);
        });
    }

    @Test
    @DisplayName("사용자 체결 거래 조회")
    public void testGetTradeByUserId() {
        Long userId = 1L;
        List<Trade> buyingTrades = Collections.singletonList(Trade.builder().build());
        List<Trade> sellingTrades = Collections.singletonList(Trade.builder().build());
        when(tradeRepository.findByBuyOrder_Buyer_Id(userId)).thenReturn(buyingTrades);
        when(tradeRepository.findBySellOrder_Seller_Id(userId)).thenReturn(sellingTrades);

        List<Trade> result = userService.getTradeByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(buyingTrades.size() + sellingTrades.size());
            verify(tradeRepository).findByBuyOrder_Buyer_Id(userId);
            verify(tradeRepository).findBySellOrder_Seller_Id(userId);
        });
    }

    @Test
    @DisplayName("사용자 질의 게시글 조회")
    public void testGetQuestionsByUserId() {
        Long userId = 1L;
        List<Post> questions = Collections.singletonList(
                Post.builder()
                        .id(1L)
                        .postType(PostType.QNA)
                        .build()
        );
        when(postRepository.findByUser_IdAndPostType(userId, PostType.QNA)).thenReturn(questions);

        List<Post> result = userService.getQuestionsByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(questions.size());
            verify(postRepository).findByUser_IdAndPostType(userId, PostType.QNA);
        });
    }
}