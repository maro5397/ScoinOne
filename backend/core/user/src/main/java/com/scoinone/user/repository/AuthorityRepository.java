package com.scoinone.user.repository;

import com.scoinone.user.entity.AuthorityEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
    Optional<AuthorityEntity> findByAuthorityName(String authorityName);
}