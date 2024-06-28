package com.shoppingmall.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shoppingmall.users.UserService;
import com.shoppingmall.users.Users;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new Users());
        return "user/register";
    }


    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute Users user) {
        if (userService.doesEmailAlreadyExists(user.getEmail())) {
         
            return "user/register"; 
        } else {
            if (user.getHashedpassword() == null || user.getHashedpassword().isEmpty()) {
               
                return "user/register"; 
            } else {
                String hashedPassword = userService.hashPassword(user.getHashedpassword());
                user.setHashedpassword(hashedPassword);
                userService.createUser2(user);
                return "redirect:/login"; 
            }
        }
    }
}