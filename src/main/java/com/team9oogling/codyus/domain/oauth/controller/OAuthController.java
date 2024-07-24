package com.team9oogling.codyus.domain.oauth.controller;


import com.team9oogling.codyus.domain.oauth.service.KakaoService;
import com.team9oogling.codyus.domain.oauth.service.NaverService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuthController {

//  private final KakaoService kakaoService;
//  private final NaverService naverService;
//
//  public OAuthController(KakaoService kakaoService, NaverService naverService) {
//    this.kakaoService = kakaoService;
//    this.naverService = naverService;
//  }
//
//  @GetMapping("/login")
//  public String loginForm(Model model) {
//    model.addAttribute("kakaoApiKey", kakaoService.getKakaoApiKey());
//    model.addAttribute("kakaoRedirectUri", kakaoService.getKakaoRedirectUri());
//    model.addAttribute("naverClientId", naverService.getClientId());
//    model.addAttribute("naverRedirectUri", naverService.getRedirectUri());
//    return "login";
//  }
//
//  @RequestMapping("/login/oauth2/code/kakao")
//  public String kakaoLogin(@RequestParam String code) {
//    String accessToken = kakaoService.getAccessToken(code);
//    Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
//
//    String email = (String) userInfo.get("email");
//    String nickname = (String) userInfo.get("nickname");
//
//    System.out.println("Kakao email = " + email);
//    System.out.println("Kakao nickname = " + nickname);
//    System.out.println("Kakao accessToken = " + accessToken);
//
//    return "redirect:/result";
//  }
//
//  @RequestMapping("/login/oauth2/code/naver")
//  public String naverLogin(@RequestParam String code) {
//    String accessToken = naverService.getAccessToken(code);
//    Map<String, Object> userInfo = naverService.getUserInfo(accessToken);
//
//    String email = (String) userInfo.get("email");
//    String nickname = (String) userInfo.get("nickname");
//
//    System.out.println("Naver email = " + email);
//    System.out.println("Naver nickname = " + nickname);
//    System.out.println("Naver accessToken = " + accessToken);
//
//    return "redirect:/result";
//  }


}
