package com.sliwinski.exhibitions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckLocations {




    @GetMapping("/check-locations")
    public String getAddExhibitionView(Model model) {
        return "check-locations";
    }

}
