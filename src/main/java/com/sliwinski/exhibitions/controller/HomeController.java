package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class HomeController {

    private final ExhibitionService exhibitionService;
    private final AuthService authService;

    public HomeController(ExhibitionService exhibitionService, AuthService authService) {
        this.exhibitionService = exhibitionService;
        this.authService = authService;
    }

    private void addUsernameAttribute(Model model){
        String username = authService.getAuthenticatedUsername();
        if(!username.equals("anonymousUser")) model.addAttribute("username", username);
    }

    @GetMapping("/")
    public String getHome(Model model) {
        addUsernameAttribute(model);
        model.addAttribute("exhibitions", exhibitionService.getAllExhibitions());
        return "home";
    }

    @GetMapping("/view-purchases")
    public String getViewPurchases(Model model) {
        addUsernameAttribute(model);
        return "view-purchases";
    }

}
