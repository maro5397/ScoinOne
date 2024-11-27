package com.scoinone.core.repository;

import com.scoinone.core.entity.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByAuthorityName(String authorityName);
}