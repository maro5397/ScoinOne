package com.scoinone.core.config;

import com.scoinone.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(4)
public class TestSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.builder()
                .username("user")
                .email("user@test.com")
                .password(passwordEncoder.encode("user"))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        UserDetails manager = User.builder()
                .username("manager")
                .email("manager@test.com")
                .password(passwordEncoder.encode("manager"))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_MANAGER")))
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .email("admin@test.com")
                .password(passwordEncoder.encode("admin"))
                .authorities(Collections.singleton((new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .build();

        return new InMemoryUserDetailsManager(user, manager, admin);
    }
}
