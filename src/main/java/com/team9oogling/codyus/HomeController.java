package com.team9oogling.codyus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/home")
  public String home() {
    return "index";
  }

  @GetMapping("/login-page")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/signup-page")
  public String signupPage() {
    return "signup";
  }

  @GetMapping("/shop-page")
  public String shopPage() {
    return "shop";
  }

  @GetMapping("/chat")
  public String chatPage() {
    return "chat";
  }

}