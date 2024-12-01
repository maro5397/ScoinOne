package com.scoinone.core.service;

import com.scoinone.core.entity.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    // 추후 다른 사용자 정보 조회 시 사용
    User getUserByEmail(String email);

    User createUser(String email, String password, String username);

    User updateUser(Long id, String newUsername);

    String deleteUser(Long id);

    List<Notification> getNotificationsFromLast30DaysByUserId(Long userId);

    List<OwnedVirtualAsset> getOwnedVirtualAssetsByUserId(Long userId);

    List<BuyOrder> getBuyOrderByUserId(Long userId);

    List<SellOrder> getSellOrderByUserId(Long userId);

    // 추후 모든 주문에 대한 조회 기능 구현 시 사용
    List<Object> getOrderByUserId(Long userId);

    List<Trade> getTradeByUserId(Long userId);

    List<Post> getQuestionsByUserId(Long userId);
}
