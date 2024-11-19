package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.user.CreateUserResponseDto;
import com.scoinone.core.dto.response.user.GetUserResponseDto;
import com.scoinone.core.dto.response.user.UpdateUserResponseDto;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.UserMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
    private UserMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testUserToCreateUserResponseDto() {
        User user = createUser(1L, "testUser", "test@example.com",
                LocalDateTime.parse("2023-11-19T10:15:30"), null);

        CreateUserResponseDto responseDto = mapper.userToCreateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(1L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("testUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("test@example.com");
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo("2023-11-19T10:15:30");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testUserToGetUserResponseDto() {
        User user = createUser(2L, "anotherUser", "another@example.com",
                LocalDateTime.parse("2023-11-19T11:00:00"), LocalDateTime.parse("2023-11-19T11:05:00"));

        GetUserResponseDto responseDto = mapper.userToGetUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(2L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("anotherUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("another@example.com");
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo("2023-11-19T11:00:00");
            softly.assertThat(responseDto.getUpdatedAt()).isEqualTo("2023-11-19T11:05:00");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testUserToUpdateUserResponseDto() {
        User user = createUser(3L, "updateUser", "update@example.com",
                null, LocalDateTime.parse("2023-11-19T12:00:00"));

        UpdateUserResponseDto responseDto = mapper.userToUpdateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(3L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("updateUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("update@example.com");
            softly.assertThat(responseDto.getUpdatedAt()).isEqualTo("2023-11-19T12:00:00");
        });
    }

    private User createUser(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}