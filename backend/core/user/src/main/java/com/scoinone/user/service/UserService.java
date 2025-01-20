package com.scoinone.user.service;

import com.scoinone.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    // 추후 다른 사용자 정보 조회 시 사용
    UserEntity getUserByEmail(String email);

    UserEntity getUser(String id);

    UserEntity createUser(String email, String password, String username);

    UserEntity updateUser(String id, String newUsername);

    String deleteUser(String id);
}
