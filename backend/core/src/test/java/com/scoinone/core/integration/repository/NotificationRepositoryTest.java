package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.NotificationStatus;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.repository.UserRepository;
import java.util.List;
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
class NotificationRepositoryTest {
    private User user;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("user@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        Notification notification1 = Notification.builder()
                .user(user)
                .content("New message received.")
                .status(NotificationStatus.UNREAD)
                .build();

        Notification notification2 = Notification.builder()
                .user(user)
                .content("Your order has been shipped.")
                .status(NotificationStatus.UNREAD)
                .build();

        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
    }

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID 기준으로 30일 전 알람 조회")
    void testFindByUser_IdAndCreatedAtAfter() {
        Long userId = user.getId();

        List<Notification> notifications = notificationRepository.findByUserIdAndLast30Days(userId);

        assertSoftly(softly -> {
            softly.assertThat(notifications).hasSize(2);
            softly.assertThat(notifications.get(0).getContent()).isEqualTo("New message received.");
            softly.assertThat(notifications.get(1).getContent()).isEqualTo("Your order has been shipped.");
        });
    }
}