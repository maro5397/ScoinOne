package com.scoinone.user.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoinone.user.dto.request.auth.SignInRequestDto;
import com.scoinone.user.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtFilter extends UsernamePasswordAuthenticationFilter {
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String AUTHORITIES_DELIMITER = ",";

    private final Long tokenValidityInMilliseconds;
    private final Key key;

    public JwtFilter(
            AuthenticationManager authenticationManager,
            Environment env
    ) {
        super.setAuthenticationManager(authenticationManager);
        String secret = env.getProperty("jwt.secret");
        String tokenValidityInSeconds = env.getProperty("jwt.token-validity-in-seconds");
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.tokenValidityInMilliseconds = Long.parseLong(Objects.requireNonNull(tokenValidityInSeconds)) * 1000;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            SignInRequestDto signInRequestDto = new ObjectMapper()
                    .readValue(request.getInputStream(), SignInRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDto.getEmail(),
                            signInRequestDto.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        UserEntity user = (UserEntity)authResult.getPrincipal();

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITIES_DELIMITER));

        String token = Jwts.builder()
                .setSubject(user.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USERNAME_KEY, user.getCustomUsername())
                .claim(EMAIL_KEY, user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidityInMilliseconds))
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}
