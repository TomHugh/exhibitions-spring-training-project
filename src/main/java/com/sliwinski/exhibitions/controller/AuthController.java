package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.service.UserService;
import com.sliwinski.exhibitions.service.validator.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

//    @PostMapping("/signin")
//    public String signinUser(Model model) {
//        return "home";
//    }


    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> body, Model model) {
        try {
            Validate.password(body.get("password"), body.get("retyped_password"));
        } catch (Exception e) {
            model.addAttribute("alert_class", "alert-danger");
            model.addAttribute("message", e.getMessage());
            return "register";
        }
        userService.createUser(body.get("username"), passwordEncoder.encode(body.get("password")));
        return "redirect:/?alert=alert-success&message=User registered. Please Log in";
    }


}
