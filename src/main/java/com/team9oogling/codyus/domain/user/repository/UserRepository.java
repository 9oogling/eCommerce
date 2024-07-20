package com.team9oogling.codyus.domain.user.repository;

import com.team9oogling.codyus.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByemail(String email);
}
