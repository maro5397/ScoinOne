package com.scoinone.core.service;

import com.scoinone.core.entity.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByEmail(String email);

    User createUser(String email, String password, String username);

    User updateUser(Long id, String newUsername);

    String deleteUser(Long id);

    List<Notification> getNotificationsFromLast30DaysByUserId(Long userId);

    List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId);

    List<BuyOrder> getBuyOrderByUserId(Long userId);

    List<SellOrder> getSellOrderByUserId(Long userId);

    List<Object> getOrderByUserId(Long userId);

    List<Trade> getTradeByUserId(Long userId);

    List<Post> getQuestionsByUserId(Long userId);
}
