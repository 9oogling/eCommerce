package com.team9oogling.codyus.global.repository;

import com.team9oogling.codyus.global.entity.BlacklistedToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
  Optional<BlacklistedToken> findByToken(String token);
  boolean existsByToken(String token);
}
