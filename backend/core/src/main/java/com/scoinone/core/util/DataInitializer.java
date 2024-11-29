package com.scoinone.core.util;

import com.scoinone.core.entity.Authority;
import com.scoinone.core.repository.AuthorityRepository;
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
        Authority roleUser = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        Authority roleAdmin = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();
        Authority roleManager = Authority.builder()
                .authorityName("ROLE_MANAGER")
                .build();
        authorityRepository.saveAll(List.of(roleUser, roleAdmin, roleManager));
    }
}