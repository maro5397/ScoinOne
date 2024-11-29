package com.scoinone.core.unit.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.auth.JwtTokenProvider;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.service.impl.AuthServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
    }

    @Test
    @DisplayName("인증 후 JWT 토큰 발급 테스트")
    public void authenticate_ValidCredentials_ReturnsToken() {
        Long userId = 1L;
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";
        String token = "mockToken";

        User user = User.builder()
                .id(userId)
                .username(username)
                .email(email)
                .password(password)
                .build();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
        when(jwtTokenProvider.createToken(authentication, userId, username)).thenReturn(token);

        String result = authService.authenticate(email, password);

        assertEquals(token, result);
        verify(authenticationManager).authenticate(authToken);
        verify(jwtTokenProvider).createToken(authentication, userId, username);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}