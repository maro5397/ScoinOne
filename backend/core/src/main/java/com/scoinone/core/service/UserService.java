package com.scoinone.core.service;

import com.scoinone.core.entity.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);

    List<Notification> getCommentsFromLast30DaysByUserId(Long userId);

    List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId);

    List<BuyOrder> getBuyOrderByUserId(Long userId);

    List<SellOrder> getSellOrderByUserId(Long userId);

    List<Object> getOrderByUserId(Long userId);

    List<Trade> getTradeByUserId(Long userId);

    List<Post> getQuestionsByUserId(Long userId);
}
