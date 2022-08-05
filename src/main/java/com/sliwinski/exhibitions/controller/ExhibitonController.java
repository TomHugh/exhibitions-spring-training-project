package com.sliwinski.exhibitions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin/")
public class ExhibitonController {




    @GetMapping("/new-exhibition")
    public String getAddExhibitionView(Model model) {
        return "new-exhibition";
    }

}
