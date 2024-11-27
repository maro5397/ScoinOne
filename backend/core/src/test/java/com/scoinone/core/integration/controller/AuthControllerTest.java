package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.request.auth.LoginRequestDto;
import com.scoinone.core.dto.response.auth.LoginResponseDto;
import com.scoinone.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class})
class AuthControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        userService.createUser("test@example.com", "password", "testUser");
    }

    @Test
    @DisplayName("로그인 시도 후 JWT 토큰 수신 테스트")
    void signIn_shouldReturnJwtAndHttpStatusOk() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");

        ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity(
                "/api/auth/signin",
                requestDto,
                LoginResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(response.getHeaders().get("Authorization"))
                    .anySatisfy(header -> softly.assertThat(header).startsWith("Bearer "));
        });
    }
}