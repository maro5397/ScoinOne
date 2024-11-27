package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.*;
import com.scoinone.core.repository.*;
import com.scoinone.core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
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
                .lastLogin(LocalDateTime.now())
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
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<Notification> getNotificationsFromLast30DaysByUserId(Long userId) {
        List<Notification> verifiableNotifications = notificationRepository.findByUserIdAndLast30Days(userId);
        if (verifiableNotifications == null || verifiableNotifications.isEmpty()) {
            throw new EntityNotFoundException("Notifications not found with userId: " + userId);
        }
        return verifiableNotifications;
    }

    @Override
    public List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId) {
        List<OwnedVirtualAsset> ownedVirtualAssets = ownedVirtualAssetRepository.findByUser_Id(userId);
        if (ownedVirtualAssets == null || ownedVirtualAssets.isEmpty()) {
            throw new EntityNotFoundException("OwnedVirtualAssets not found with userId: " + userId);
        }
        return ownedVirtualAssets;
    }

    @Override
    public List<BuyOrder> getBuyOrderByUserId(Long userId) {
        List<BuyOrder> buyOrders = buyOrderRepository.findByBuyer_IdAndStatus(userId, OrderStatus.PENDING);
        if (buyOrders == null || buyOrders.isEmpty()) {
            throw new EntityNotFoundException("BuyOrder not found with userId: " + userId);
        }
        return buyOrders;
    }

    @Override
    public List<SellOrder> getSellOrderByUserId(Long userId) {
        List<SellOrder> sellOrders = sellOrderRepository.findBySeller_IdAndStatus(userId, OrderStatus.PENDING);
        if (sellOrders == null || sellOrders.isEmpty()) {
            throw new EntityNotFoundException("SellOrder not found with userId: " + userId);
        }
        return sellOrders;
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
        if (buyingTrades == null || buyingTrades.isEmpty()) {
            throw new EntityNotFoundException("Buying trades not found with userId: " + userId);
        }
        List<Trade> sellingTrades = tradeRepository.findBySellOrder_Seller_Id(userId);
        if (sellingTrades == null || sellingTrades.isEmpty()) {
            throw new EntityNotFoundException("Selling trades not found with userId: " + userId);
        }

        List<Trade> allTrades = new CopyOnWriteArrayList<>();
        allTrades.addAll(buyingTrades);
        allTrades.addAll(sellingTrades);

        allTrades.sort(Comparator.comparing(Trade::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        return allTrades;
    }

    @Override
    public List<Post> getQuestionsByUserId(Long userId) {
        List<Post> questions = postRepository.findByUser_IdAndPostType(userId, PostType.QNA);
        if (questions == null || questions.isEmpty()) {
            throw new EntityNotFoundException("Questions not found with userId: " + userId);
        }
        return questions;
    }
}
