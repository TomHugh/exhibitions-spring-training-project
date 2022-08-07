package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.dto.mapper.UserDtoMapper;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping
public class HomeController {

    private final ExhibitionService exhibitionService;
    private final AuthService authService;
    private final OrderService orderService;

    public HomeController(ExhibitionService exhibitionService, AuthService authService, OrderService orderService, OrderDtoMapper orderDtoMapper) {
        this.exhibitionService = exhibitionService;
        this.authService = authService;
        this.orderService = orderService;
    }


    @GetMapping("/")
    public String getHome(Model model) {
        authService.addUsernameAttribute(model);
        model.addAttribute("exhibitions", exhibitionService.getAllExhibitions());
        return "home";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        authService.addUsernameAttribute(model);
        List<OrderDto> orders = orderService.getUserOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }
}
