package com.sliwinski.exhibitions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

//    private final UserService userService;
//    private final ExhibitionService exhibitionService;
//    private final OrderService orderService;
//
//    public LoginController(UserService userService, ExhibitionService exhibitionService, OrderService orderService) {
//        this.userService = userService;
//        this.exhibitionService = exhibitionService;
//        this.orderService = orderService;
//    }




    @GetMapping
    public String getAdminView(Model model) {
        return "register";
    }

}
