package com.team9oogling.codyus.global.config;

import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.global.security.UserDetailsServiceImpl;
import com.team9oogling.codyus.domain.user.service.UserService;
import com.team9oogling.codyus.global.dto.SecurityResponse;
import com.team9oogling.codyus.global.jwt.JwtProvider;
import com.team9oogling.codyus.global.security.CustomAuthenticationEntryPoint;
import com.team9oogling.codyus.global.security.JwtAuthenticationFilter;
import com.team9oogling.codyus.global.security.JwtAuthorizationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final UserRepository userRepository;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final SecurityResponse securityResponse;
  private final UserService userService;

  public SecurityConfig(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService,
      AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
      SecurityResponse securityResponse, UserService userService) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
    this.authenticationConfiguration = authenticationConfiguration;
    this.userRepository = userRepository;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    this.securityResponse = securityResponse;
    this.userService = userService;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {

    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userRepository,
        securityResponse);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

    return filter;
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtProvider, userDetailsService, securityResponse, userService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf((csrf) -> csrf.disable());

    http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            // 1. 중복 url 삭제 2. 파일명 -> API 로 변경
            .requestMatchers("/chatting/**","chat.html","login.html","/api/users/signup",
                "/api/users/token/refresh", "/api/users/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/user/kakao/callback", "/api/users/login/naver",
                "/api/users/naver/callback", "/api/posts", "/login-page", "/home", "/api/users/email").permitAll()
            .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated())
        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint);
        })
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

    return http.build();
  }
}