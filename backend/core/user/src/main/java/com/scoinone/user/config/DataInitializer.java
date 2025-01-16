package com.scoinone.user.config;

import com.scoinone.user.entity.AuthorityEntity;
import com.scoinone.user.repository.AuthorityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataInitializer implements ApplicationRunner {

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) {
        AuthorityEntity roleUser = AuthorityEntity.builder()
                .authorityName("ROLE_USER")
                .build();
        AuthorityEntity roleAdmin = AuthorityEntity.builder()
                .authorityName("ROLE_ADMIN")
                .build();
        AuthorityEntity roleManager = AuthorityEntity.builder()
                .authorityName("ROLE_MANAGER")
                .build();
        authorityRepository.saveAll(List.of(roleUser, roleAdmin, roleManager));
    }
}