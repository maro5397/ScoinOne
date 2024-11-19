package com.scoinone.core.service.impl;

import com.scoinone.core.common.OrderStatus;
import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.*;
import com.scoinone.core.repository.*;
import com.scoinone.core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User is already Existed!");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existedUser = getUserById(id);
        existedUser.setUsername(updatedUser.getUsername());
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<Notification> getCommentsFromLast30DaysByUserId(Long userId) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return notificationRepository.findByUser_UserIdAndCreatedAtAfter(userId, thirtyDaysAgo)
                .orElseThrow(() -> new EntityNotFoundException("Notifications not found with userId: " + userId));
    }

    @Override
    public List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId) {
        return ownedVirtualAssetRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("OwnedVirtualAssets not found with userId: " + userId));
    }

    @Override
    public List<BuyOrder> getBuyOrderByUserId(Long userId) {
        return buyOrderRepository.findByBuyer_UserIdAndStatus(userId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException("BuyOrder not found with userId: " + userId));
    }

    @Override
    public List<SellOrder> getSellOrderByUserId(Long userId) {
        return sellOrderRepository.findBySeller_UserIdAndStatus(userId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException("SellOrder not found with userId: " + userId));
    }

    @Override
    public List<Object> getOrderByUserId(Long userId) {
        List<BuyOrder> buyOrders = getBuyOrderByUserId(userId);
        List<SellOrder> sellOrders = getSellOrderByUserId(userId);

        List<Object> allOrders = new CopyOnWriteArrayList<>();
        allOrders.addAll(buyOrders);
        allOrders.addAll(sellOrders);

        allOrders.sort(Comparator.comparing(order -> {
            if (order instanceof BuyOrder) {
                return ((BuyOrder) order).getCreatedAt();
            } else if (order instanceof SellOrder) {
                return ((SellOrder) order).getCreatedAt();
            } else {
                throw new NoSuchElementException("Unknown Instance Not BuyOrder, SellOrder");
            }
        }));

        return allOrders;
    }

    @Override
    public List<Trade> getTradeByUserId(Long userId) {
        List<Trade> buyingTrades = tradeRepository.findByBuyOrder_Buyer_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("BuyingTrade not found with userId: " + userId));
        List<Trade> sellingTrades = tradeRepository.findBySellOrder_Seller_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("SellingTrade not found with userId: " + userId));

        List<Trade> allTrades = new CopyOnWriteArrayList<>();
        allTrades.addAll(buyingTrades);
        allTrades.addAll(sellingTrades);

        allTrades.sort(Comparator.comparing(Trade::getCreatedAt));
        return allTrades;
    }

    @Override
    public List<Post> getQuestionsByUserId(Long userId) {
        return postRepository.findByUser_UserIdAndPostType(userId, PostType.QNA)
                .orElseThrow(() -> new EntityNotFoundException("Questions not found with userId: " + userId));
    }
}
