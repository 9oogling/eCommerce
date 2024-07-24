package com.team9oogling.codyus.global.security;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.entity.StatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email).orElseThrow( () -> new CustomException(StatusCode.NOT_FOUND_USER));

    return new UserDetailsImpl(user);
  }

}
