package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.*;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.UserIdNameRoleService;
import com.sliwinski.exhibitions.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Consists of main view for administrator.
 * The management of exhibitions and locations is separated in the respective controllers (ExhibitionController, LocationController).
 */

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserIdNameRoleService userIdNameRoleService;
    private final ExhibitionService exhibitionService;
    private final OrderService orderService;

    @GetMapping
    public String getDashboard(Model model) {
        model.addAttribute("totalExhibitions", exhibitionService.getQuantity());
        model.addAttribute("totalUsers", userService.getUserQuantity());
        model.addAttribute("totalAdmins", userService.getAdminQuantity());
        model.addAttribute("totalTicketPurchases", orderService.getQuantity());
        model.addAttribute("profit", orderService.getProfit());
        return "dashboard";
    }

    @GetMapping("/users")
    public String getUsers(@RequestParam(required = false) Integer page, Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        Page<UserIdNameRole> usersPage = userIdNameRoleService.getAllUsers(pageNumber-1);
        model.addAttribute("page", usersPage.getTotalPages());
        model.addAttribute("users", usersPage.getContent());
        return "users";
    }
}
