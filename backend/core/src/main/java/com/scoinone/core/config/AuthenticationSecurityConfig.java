package com.scoinone.core.config;

import com.scoinone.core.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class AuthenticationSecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain authenticationSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/signup",
                                "/login",
                                "/reset-password"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .loginProcessingUrl("/api/user/login")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                RequestCache requestCache = new HttpSessionRequestCache();
                                SavedRequest savedRequest = requestCache.getRequest(request, response);
                                String redirectUrl = savedRequest.getRedirectUrl();
                                response.sendRedirect(redirectUrl);
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/api/user/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("remember")
                )
                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeParameter("remember")
                        .tokenValiditySeconds(3600)
                        .alwaysRemember(false)
                        .userDetailsService(userService)
                )
                .sessionManagement((session) -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                )
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                response.sendRedirect("/login");
                            }
                        }));
        return http.build();
    }
}
