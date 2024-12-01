package com.scoinone.core.config;

import com.scoinone.core.entity.Authority;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.UserAuthority;
import com.scoinone.core.repository.UserRepository;
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
        User user = User.builder()
                .username("user")
                .email("user@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        Authority roleUser = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .authority(roleUser)
                .build();

        user.setUserAuthorities(Set.of(userAuthority));

        User manager = User.builder()
                .username("manager")
                .email("manager@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        Authority roleManager = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();

        UserAuthority managerAuthority = UserAuthority.builder()
                .user(manager)
                .authority(roleManager)
                .build();

        manager.setUserAuthorities(Set.of(managerAuthority));

        User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("password"))
                .lastLogin(LocalDateTime.now())
                .build();

        Authority roleAdmin = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        UserAuthority adminAuthority = UserAuthority.builder()
                .user(admin)
                .authority(roleAdmin)
                .build();

        admin.setUserAuthorities(Set.of(adminAuthority));

        userRepository.saveAll(List.of(admin, manager, user));
    }
}
