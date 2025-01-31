package com.scoinone.user.entity;

import com.scoinone.user.entity.base.UpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UserEntity extends UpdatableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private String id;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAuthorityEntity> userAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthorities.stream().map(UserAuthorityEntity::getAuthority).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getCustomUsername() {
        return username;
    }
}