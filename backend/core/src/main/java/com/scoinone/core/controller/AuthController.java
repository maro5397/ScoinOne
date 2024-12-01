package com.scoinone.core.controller;

import com.scoinone.core.dto.request.auth.LoginRequestDto;
import com.scoinone.core.dto.response.auth.LoginResponseDto;
import com.scoinone.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signIn(@Valid @RequestBody LoginRequestDto requestDto) {
        String jwt = authService.authenticate(requestDto.getEmail(), requestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(jwt);

        return new ResponseEntity<>(loginResponseDto, httpHeaders, HttpStatus.OK);
    }
}
