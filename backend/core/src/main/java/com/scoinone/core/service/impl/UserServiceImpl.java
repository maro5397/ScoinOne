package com.scoinone.core.service.impl;

import com.scoinone.core.common.status.OrderStatus;
import com.scoinone.core.common.type.PostType;
import com.scoinone.core.entity.*;
import com.scoinone.core.repository.*;
import com.scoinone.core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OwnedVirtualAssetRepository ownedVirtualAssetRepository;
    private final NotificationRepository notificationRepository;
    private final BuyOrderRepository buyOrderRepository;
    private final SellOrderRepository sellOrderRepository;
    private final TradeRepository tradeRepository;
    private final PostRepository postRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final Clock clock;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public User createUser(String email, String password, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User is already Existed!");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .lastLogin(LocalDateTime.now(clock))
                .build();

        Authority authority = authorityRepository.findByAuthorityName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Authority not found!"));

        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .authority(authority)
                .build();

        user.setUserAuthorities(Set.of(userAuthority));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String newUsername) {
        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        existedUser.setUsername(newUsername);
        return existedUser;
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<Notification> getNotificationsFromLast30DaysByUserId(Long userId) {
        return notificationRepository.findByUserIdAndLast30Days(userId);
    }

    @Override
    public List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId) {
        return ownedVirtualAssetRepository.findByUser_Id(userId);
    }

    @Override
    public List<BuyOrder> getBuyOrderByUserId(Long userId) {
        return buyOrderRepository.findByBuyer_IdAndStatus(userId, OrderStatus.PENDING);
    }

    @Override
    public List<SellOrder> getSellOrderByUserId(Long userId) {
        return sellOrderRepository.findBySeller_IdAndStatus(userId, OrderStatus.PENDING);
    }

    @Override
    public List<Object> getOrderByUserId(Long userId) {
        List<BuyOrder> buyOrders = getBuyOrderByUserId(userId);
        List<SellOrder> sellOrders = getSellOrderByUserId(userId);

        List<Object> allOrders = new CopyOnWriteArrayList<>();
        allOrders.addAll(buyOrders);
        allOrders.addAll(sellOrders);

        allOrders.sort(Comparator.comparing(order -> {
            LocalDateTime createdAt = null;
            if (order instanceof BuyOrder) {
                createdAt = ((BuyOrder) order).getCreatedAt();
            } else if (order instanceof SellOrder) {
                createdAt = ((SellOrder) order).getCreatedAt();
            }
            return createdAt;
        }, Comparator.nullsLast(Comparator.naturalOrder())));

        return allOrders;
    }

    @Override
    public List<Trade> getTradeByUserId(Long userId) {
        List<Trade> buyingTrades = tradeRepository.findByBuyOrder_Buyer_Id(userId);
        List<Trade> sellingTrades = tradeRepository.findBySellOrder_Seller_Id(userId);

        Set<Trade> tradeSet = new HashSet<>();
        tradeSet.addAll(buyingTrades);
        tradeSet.addAll(sellingTrades);

        List<Trade> allTrades = new ArrayList<>(tradeSet);
        allTrades.sort(Comparator.comparing(Trade::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())));

        return allTrades;
    }

    @Override
    public List<Post> getQuestionsByUserId(Long userId) {
        return postRepository.findByUser_IdAndPostType(userId, PostType.QNA);
    }
}
