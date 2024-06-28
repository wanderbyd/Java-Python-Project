package com.shoppingmall.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingmall.users.UserService;
import com.shoppingmall.users.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String loginForm() {
      return "user/login";
  }



  @PostMapping("/login")
  public String loginSubmit(@RequestParam String email, @RequestParam String password, HttpSession session) {
      
      Users user = userService.findUserByEmail(email).orElse(null);
      if (user != null) {
         
          String hashedPassword = userService.hashPassword(password);
          if (hashedPassword != null && hashedPassword.equals(user.getHashedpassword())) {
            
              session.setAttribute("loggedInUser", user);
              
              if (user.getEmail().equals("admin@admin.com")) {
					session.setAttribute("isAdmin", "true");
				} else {
					session.setAttribute("isAdmin", "false");
				}

              return "redirect:/"; 
          }
      }
      return "user/login"; 
  }
  
  @GetMapping("/dashboard")
  public String dashboard(HttpSession session, Model model) {
      Users loggedInUser = (Users) session.getAttribute("loggedInUser");

      if (loggedInUser != null) {
          
          model.addAttribute("loggedInUser", loggedInUser);
          return "main";
      } else {
          return "redirect:/login"; 
      }
  }
  
  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
          session.invalidate(); 
      }
      return "redirect:/"; 
  }



}