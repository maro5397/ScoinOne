package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
class UserRepositoryTest {
    private User user;

    @Autowired
    private UserRepository userRepository;

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
                softly.assertThat(user.getEmail()).isEqualTo(email);
                softly.assertThat(user.getCustomUsername()).isEqualTo("testUser");
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