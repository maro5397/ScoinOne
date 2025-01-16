package com.scoinone.user.service.impl;

import com.scoinone.user.entity.AuthorityEntity;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.OwnedVirtualAssetEntity;
import com.scoinone.user.entity.UserAuthorityEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.AuthorityRepository;
import com.scoinone.user.repository.NotificationRepository;
import com.scoinone.user.repository.OwnedVirtualAssetRepository;
import com.scoinone.user.repository.UserRepository;
import com.scoinone.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OwnedVirtualAssetRepository ownedVirtualAssetRepository;
    private final NotificationRepository notificationRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final Clock clock;

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserEntity getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserEntity createUser(String email, String password, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User is already Existed!");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .lastLogin(LocalDateTime.now(clock))
                .build();

        AuthorityEntity authority = authorityRepository.findByAuthorityName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Authority not found!"));

        UserAuthorityEntity userAuthority = UserAuthorityEntity.builder()
                .user(user)
                .authority(authority)
                .build();

        user.setUserAuthorities(Set.of(userAuthority));

        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(String id, String newUsername) {
        UserEntity existedUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        existedUser.setUsername(newUsername);
        return existedUser;
    }

    @Override
    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<NotificationEntity> getNotificationsFromLast30DaysByUserId(String userId) {
        return notificationRepository.findByUserIdAndLast30Days(userId);
    }

    @Override
    public List<OwnedVirtualAssetEntity> getOwnedVirtualAssetsByUserId(String userId) {
        return ownedVirtualAssetRepository.findByUser_Id(userId);
    }
}
