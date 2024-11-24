package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = CoreApplication.class)
class UserRepositoryTest {
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Container
    static MySQLContainer<?> sqlContainer = new MySQLContainer<>(
            "mysql:8.0.34"
    );

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", sqlContainer::getDriverClassName);
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.~username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 사용자 조회")
    void testFindByEmail() {
        String email = user.getEmail();

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertSoftly(softly -> {
            softly.assertThat(foundUser.isPresent()).isTrue();
            foundUser.ifPresent(user -> {
                softly.assertThat(foundUser.get().getEmail()).isEqualTo(email);
                softly.assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
            });
        });
    }

    @Test
    @DisplayName("없는 이메일로 사용자 조회")
    void testFindByEmail_NotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertSoftly(softly -> {
            softly.assertThat(foundUser.isPresent()).isFalse();
        });
    }
}