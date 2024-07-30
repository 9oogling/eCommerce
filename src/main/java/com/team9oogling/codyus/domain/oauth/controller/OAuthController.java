package com.team9oogling.codyus.domain.oauth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team9oogling.codyus.domain.oauth.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class OAuthController {

  private final KakaoService kakaoService;

  public OAuthController(KakaoService kakaoService) {
    this.kakaoService = kakaoService;
  }

  @GetMapping("/user/kakao/callback")
  public String kakaoLogin(@RequestParam String code, HttpServletResponse response)
      throws JsonProcessingException {
    String token = kakaoService.kakaoLogin(code);

    Cookie cookie = new Cookie("Authorization", token);
    cookie.setPath("/");
    response.addCookie(cookie);

    System.out.println("Cookie Set: " + cookie.getName() + "=" + cookie.getValue());

    return "redirect:/home";
  }

}
