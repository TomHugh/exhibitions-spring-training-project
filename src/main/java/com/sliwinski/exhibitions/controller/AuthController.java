package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/signin")
    public String getSignin(Model model) {
        return "signin";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> body) {
//        User user = new User();
//        user.setUsername(body.get("username"));
//        user.setPassword(passwordEncoder.encode(body.get("password")));
//        user.setRole(Role.USER);
        userService.createUser(body.get("username"), passwordEncoder.encode(body.get("password")));
        return "redirect:/";
    }


}
