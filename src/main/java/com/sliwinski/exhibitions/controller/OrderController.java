package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.OrderDetailsDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.utility.Cart;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Consists of views related to order entity.
 * Both for the user and the administrator.
 */

@Controller
@AllArgsConstructor
@SessionAttributes("cart")
public class OrderController {
    private final OrderService orderService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @ModelAttribute("cart")
    public Cart newCart() {
        return new Cart();
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        authService.addUsernameAttribute(model);
        List<OrderDto> orders = orderService.getUserOrders()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(toList());

        model.addAttribute("orders", orders);
        return "user-orders";
    }

    @GetMapping("/admin/orders")
    public String getOrders(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer exhibitionId, Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        Page<Order> ordersPage;
        if(exhibitionId == null) {
            ordersPage = orderService.getAllOrders(pageNumber-1);
        } else {
            ordersPage = orderService.getByExhibitionId(exhibitionId, pageNumber-1);
        }
        List<OrderDetailsDto> orders = ordersPage.getContent().stream()
                .map(order -> modelMapper.map(order, OrderDetailsDto.class))
                .collect(toList());
        model.addAttribute("page", ordersPage.getTotalPages());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/buy")
    public String createOrder(@ModelAttribute Cart cart,  Model model, RedirectAttributes redirectAttributes) {
        orderService.saveCart(cart);
        cart = newCart();
        model.addAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "tickets_purchased");
        return "redirect:/exhibitions";
    }
}
