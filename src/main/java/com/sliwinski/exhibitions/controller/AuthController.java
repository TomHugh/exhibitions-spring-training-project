package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.service.UserService;
import com.sliwinski.exhibitions.service.validator.Validate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Consists of views related to register and authenticate users.
 * Both for the user and the administrator.
 */

@Controller
@AllArgsConstructor
@RequestMapping
public class AuthController {
    private final UserService userService;
    private final Validate validate;



    @GetMapping("/signin")
    public String getSignin(Model model) {
        return "signin";
    }

    @GetMapping("/bad-credentials")
    public String getBadCredentials(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("class", "alert-danger");
        redirectAttributes.addFlashAttribute("message", "bad_credentials");
        return "redirect:/signin";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> body, Model model, RedirectAttributes redirectAttributes) {
        validate.password(body.get("password"), body.get("retypedPassword"));
        userService.createUser(body.get("username"), body.get("password"));
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "user_registered");
        return "redirect:/";
    }

    @GetMapping("admin/new-admin")
    public String getNewAdmin(Model model) {
        return "new-admin";
    }

    @PostMapping("admin/new-admin")
    public String registerAdmin(@RequestParam Map<String, String> body, Model model, RedirectAttributes redirectAttributes) {
        validate.password(body.get("password"), body.get("retypedPassword"));
        userService.createAdmin(body.get("username"), body.get("password"));
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "admin_created");
        return "redirect:/admin";
    }
}
