package com.scoinone.user.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.user.dto.response.user.CreateUserResponseDto;
import com.scoinone.user.dto.response.user.GetUserResponseDto;
import com.scoinone.user.dto.response.user.UpdateUserResponseDto;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

    private UserMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testUserToCreateUserResponseDto() {
        UserEntity user = createUser(testUserId, "testUser", "test@example.com");

        CreateUserResponseDto responseDto = mapper.userToCreateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(testUserId);
            softly.assertThat(responseDto.getUsername()).isEqualTo("testUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("test@example.com");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testUserToGetUserResponseDto() {
        UserEntity user = createUser(testUserId, "anotherUser", "another@example.com");

        GetUserResponseDto responseDto = mapper.userToGetUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(testUserId);
            softly.assertThat(responseDto.getUsername()).isEqualTo("anotherUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("another@example.com");
        });
    }

    @Test
    @DisplayName("사용자 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testUserToUpdateUserResponseDto() {
        UserEntity user = createUser(testUserId, "updateUser", "update@example.com");

        UpdateUserResponseDto responseDto = mapper.userToUpdateUserResponseDto(user);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getUserId()).isEqualTo(testUserId);
            softly.assertThat(responseDto.getUsername()).isEqualTo("updateUser");
            softly.assertThat(responseDto.getEmail()).isEqualTo("update@example.com");
        });
    }

    private UserEntity createUser(String id, String username, String email) {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}