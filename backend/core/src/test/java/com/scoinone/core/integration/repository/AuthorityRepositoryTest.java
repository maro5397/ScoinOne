package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.Authority;
import com.scoinone.core.repository.AuthorityRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
@ActiveProfiles("dev")
class AuthorityRepositoryTest {
    @Autowired
    private AuthorityRepository authorityRepository;

    @BeforeEach
    public void setUp() {
        Authority roleUser = Authority.builder().authorityName("ROLE_USER").build();
        Authority roleAdmin = Authority.builder().authorityName("ROLE_ADMIN").build();
        authorityRepository.save(roleUser);
        authorityRepository.save(roleAdmin);
    }

    @Test
    @DisplayName("권한 이름 조회")
    public void testFindByAuthorityName() {
        Optional<Authority> foundAuthority = authorityRepository.findByAuthorityName("ROLE_USER");
        assertSoftly((softly) -> {
            softly.assertThat(foundAuthority).isPresent();
            foundAuthority.ifPresent(authority -> {
                softly.assertThat(authority.getAuthorityName()).isEqualTo("ROLE_USER");
            });
        });
    }

    @Test
    @DisplayName("없는 권한 이름 조회")
    public void testFindByAuthorityName_NotFound() {
        Optional<Authority> foundAuthority = authorityRepository.findByAuthorityName("ROLE_NON_EXISTENT");
        assertSoftly((softly) -> {
            softly.assertThat(foundAuthority).isNotPresent();
        });
    }
}