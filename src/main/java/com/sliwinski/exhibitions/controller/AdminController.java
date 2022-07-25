package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ExhibitionService exhibitionService;
    private final OrderService orderService;

    public AdminController(UserService userService, ExhibitionService exhibitionService, OrderService orderService) {
        this.userService = userService;
        this.exhibitionService = exhibitionService;
        this.orderService = orderService;
    }




    @GetMapping
    public String getAdminView(Model model) {
        model.addAttribute("exhibitions", exhibitionService.getAllExhibitons());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin";
    }

}
