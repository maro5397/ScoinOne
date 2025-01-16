package com.scoinone.user.config;

import com.scoinone.user.entity.AuthorityEntity;
import com.scoinone.user.entity.UserAuthorityEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@Profile("dev")
@Order(100)
public class UserDataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        UserEntity user = UserEntity.builder()
                .username("user")
                .email("user@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        AuthorityEntity roleUser = AuthorityEntity.builder()
                .authorityName("ROLE_USER")
                .build();

        UserAuthorityEntity userAuthority = UserAuthorityEntity.builder()
                .user(user)
                .authority(roleUser)
                .build();

        user.setUserAuthorities(Set.of(userAuthority));

        UserEntity manager = UserEntity.builder()
                .username("manager")
                .email("manager@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        AuthorityEntity roleManager = AuthorityEntity.builder()
                .authorityName("ROLE_MANAGER")
                .build();

        UserAuthorityEntity managerAuthority = UserAuthorityEntity.builder()
                .user(manager)
                .authority(roleManager)
                .build();

        manager.setUserAuthorities(Set.of(managerAuthority));

        UserEntity admin = UserEntity.builder()
                .username("admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        AuthorityEntity roleAdmin = AuthorityEntity.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        UserAuthorityEntity adminAuthority = UserAuthorityEntity.builder()
                .user(admin)
                .authority(roleAdmin)
                .build();

        admin.setUserAuthorities(Set.of(adminAuthority));

        userRepository.saveAll(List.of(admin, manager, user));
    }
}
