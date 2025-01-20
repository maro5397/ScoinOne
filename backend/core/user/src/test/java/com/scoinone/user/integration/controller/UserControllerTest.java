package com.scoinone.user.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.user.config.TestContainerConfig;
import com.scoinone.user.config.UserDataInitializer;
import com.scoinone.user.dto.common.DeleteResponseDto;
import com.scoinone.user.dto.request.user.CreateUserRequestDto;
import com.scoinone.user.dto.request.user.UpdateUserRequestDto;
import com.scoinone.user.dto.response.user.CreateUserResponseDto;
import com.scoinone.user.dto.response.user.GetUserResponseDto;
import com.scoinone.user.dto.response.user.UpdateUserResponseDto;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.service.UserService;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class, UserDataInitializer.class})
@ActiveProfiles("dev")
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private UserEntity savedUser;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        savedUser = userService.getUserByEmail("user@example.com");

        headers = new HttpHeaders();
        headers.set("UserId", savedUser.getId());
        headers.set("Content-Type", "application/json");
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    void createUser_shouldReturnCreatedUser() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("newUser@example.com");
        requestDto.setPassword("newPassword");
        requestDto.setUsername("newUser");

        ResponseEntity<CreateUserResponseDto> response = restTemplate.exchange(
                "/api/user/signup",
                HttpMethod.POST,
                new HttpEntity<>(requestDto),
                CreateUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getEmail()).isEqualTo("newUser@example.com");
            softly.assertThat(response.getBody().getUsername()).isEqualTo("newUser");
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    void getUser_shouldReturnUser() {
        ResponseEntity<GetUserResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GetUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getEmail()).isEqualTo(savedUser.getEmail());
            softly.assertThat(response.getBody().getUsername()).isEqualTo(savedUser.getCustomUsername());
        });
    }

    @Test
    @DisplayName("사용자 수정 테스트")
    void updateUser_shouldReturnUpdatedUser() {
        UpdateUserRequestDto updateRequestDto = new UpdateUserRequestDto();
        updateRequestDto.setUsername("updatedUser");

        ResponseEntity<UpdateUserResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateUserResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo("updatedUser");
        });
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteUser_shouldReturnOk() {
        UserEntity newUser = userService.createUser("secondUser@example.com", "password", "secondUser");

        HttpHeaders headers = new HttpHeaders();
        headers.set("UserId", newUser.getId());
        headers.set("Content-Type", "application/json");

        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/user",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("User deleted successfully");
        });
    }
}