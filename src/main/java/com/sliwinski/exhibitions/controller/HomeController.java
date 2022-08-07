package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@RequestMapping
public class HomeController {

    private final ExhibitionService exhibitionService;
    private final AuthService authService;
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    public HomeController(ExhibitionService exhibitionService, AuthService authService, OrderService orderService, OrderDtoMapper orderDtoMapper, OrderDtoMapper orderDtoMapper1) {
        this.exhibitionService = exhibitionService;
        this.authService = authService;
        this.orderService = orderService;
        this.orderDtoMapper = orderDtoMapper;
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
        List<OrderDto> orders = orderService.getUserOrders()
                .stream()
                .map(orderDtoMapper::toDto)
                .collect(toList());

        model.addAttribute("orders", orders);
        return "orders";
    }
}
