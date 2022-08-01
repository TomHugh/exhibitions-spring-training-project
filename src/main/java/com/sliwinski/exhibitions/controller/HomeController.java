package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.service.ExhibitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ExhibitionService exhibitionService;

    public HomeController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("exhibitions", exhibitionService.getAllExhibitions());
        return "home";
    }

}
