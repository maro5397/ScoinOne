package com.scoinone.core.entity;

import com.scoinone.core.entity.base.UpdatableEntity;
import jakarta.persistence.*;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends UpdatableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Setter
    private String username;

    @Getter
    private String email;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private LocalDateTime lastLogin;

    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAuthority> userAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthorities.stream().map(UserAuthority::getAuthority).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getCustomUsername() {
        return username;
    }
}