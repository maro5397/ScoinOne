package com.scoinone.user.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.user.entity.AuthorityEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.AuthorityRepository;
import com.scoinone.user.repository.UserRepository;
import com.scoinone.user.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private Clock clock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clock.instant()).thenReturn(Instant.parse("2024-11-21T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
    }

    @Test
    @DisplayName("사용자 조회 테스트(ID)")
    public void testGetUserById_Success() {
        UserEntity user = UserEntity.builder()
                .id(testUserId)
                .email("test@example.com")
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserEntity result = userService.getUser(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findById(user.getId());
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트(이메일)")
    public void testGetUserByEmail_Success() {
        UserEntity user = UserEntity.builder()
                .id(testUserId)
                .email("test@example.com")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserByEmail("test@example.com");

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findByEmail(user.getEmail());
        });
    }

    @Test
    @DisplayName("사용자 조회 테스트 실패")
    public void testGetUserById_NotFound() {
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.getUser(testUserId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User not found with id: " + testUserId);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트")
    public void testCreateUser_Success() {
        String email = "test@example.com";
        String password = "securePassword";
        String username = "testUser";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(authorityRepository.findByAuthorityName("ROLE_USER"))
                .thenReturn(Optional.of(AuthorityEntity.builder().authorityName("ROLE_USER").build()));

        userService.createUser(email, password, username);

        ArgumentCaptor<UserEntity> userCaptor = forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        UserEntity savedUser = userCaptor.getValue();
        assertSoftly(softly -> {
            softly.assertThat(savedUser).isNotNull();
            softly.assertThat(savedUser.getEmail()).isEqualTo(email);
            softly.assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
            softly.assertThat(savedUser.getCustomUsername()).isEqualTo(username);
            softly.assertThat(savedUser.getAuthorities()).hasSize(1);
        });
    }

    @Test
    @DisplayName("사용자 생성 (회원가입) 테스트 실패 - 이미 가입한 사용자")
    public void testCreateUser_UserAlreadyExists() {
        UserEntity user = UserEntity.builder()
                .username("testUser")
                .email("test@example.com")
                .password("securePassword")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.createUser(
                            user.getEmail(),
                            user.getPassword(),
                            user.getCustomUsername()
                    ))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("User is already Existed!");
        });
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    public void testUpdateUser_Success() {
        UserEntity existingUser = UserEntity.builder()
                .id(testUserId)
                .username("OldUsername")
                .build();

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(existingUser));

        UserEntity result = userService.updateUser(testUserId, "NewUsername");

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(existingUser);
            softly.assertThat(existingUser.getCustomUsername()).isEqualTo("NewUsername");
            verify(userRepository).findById(testUserId);
        });
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    public void testDeleteUser() {
        userService.deleteUser(testUserId);

        verify(userRepository).deleteById(testUserId);
    }

    @Test
    @DisplayName("사용자 닉네임(이메일)으로 사용자 조회 테스트 - spring security")
    public void testLoadUserByUsername_Success() {
        String email = "test@example.com";
        UserEntity user = UserEntity.builder().build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(email);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(userRepository).findByEmail(email);
        });
    }

    @Test
    @DisplayName("사용자 닉네임(이메일)으로 사용자 조회 테스트 실패 - spring security")
    public void testLoadUserByUsername_NotFound() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> userService.loadUserByUsername(email))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("User not found with email: " + email);
        });
    }

//    @Test
//    @DisplayName("사용자 보유 가상자산 조회")
//    public void testGetOwnedVirtualAssetsByUserId() {
//        List<OwnedVirtualAssetEntity> ownedAssets = Collections.singletonList(OwnedVirtualAssetEntity.builder().build());
//        when(ownedVirtualAssetRepository.findByUser_Id(testUserId)).thenReturn(ownedAssets);
//
//        List<OwnedVirtualAssetEntity> result = userService.getOwnedVirtualAssetsByUserId(testUserId);
//
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            verify(ownedVirtualAssetRepository).findByUser_Id(testUserId);
//        });
//    }
}