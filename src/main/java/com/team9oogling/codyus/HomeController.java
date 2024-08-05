package com.team9oogling.codyus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

  @GetMapping("/posts")
  public String postsPage() {
    return "posts";
  }

  @GetMapping("/posts/postCreate")
  public String postCreatePage() {
    return "postCreate";
  }

  @GetMapping("/posts/postDetail/{postId}")
  public String postDetailPage(@PathVariable("postId") int postId) {
    return "postDetail";
  }

  @GetMapping("/posts/postSearch")
  public String postSearchPage() {
    return "postSearch";
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