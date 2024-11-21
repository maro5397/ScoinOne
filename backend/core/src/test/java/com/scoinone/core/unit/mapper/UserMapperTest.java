package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.user.CreateUserResponseDto;
import com.scoinone.core.dto.response.user.GetUserResponseDto;
import com.scoinone.core.dto.response.user.UpdateUserResponseDto;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.UserMapper;
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
        User user = createUser(1L, "testUser", "test@example.com");

        CreateUserResponseDto responseDto = mapper.userToCreateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(1L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("testUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("test@example.com");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testUserToGetUserResponseDto() {
        User user = createUser(2L, "anotherUser", "another@example.com");

        GetUserResponseDto responseDto = mapper.userToGetUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(2L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("anotherUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("another@example.com");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testUserToUpdateUserResponseDto() {
        User user = createUser(3L, "updateUser", "update@example.com");

        UpdateUserResponseDto responseDto = mapper.userToUpdateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(3L);
            softly.assertThat(responseDto.getUsername()).isEqualTo("updateUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("update@example.com");
        });
    }

    private User createUser(Long id, String username, String email) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}