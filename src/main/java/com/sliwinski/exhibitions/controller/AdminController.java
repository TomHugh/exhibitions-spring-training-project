package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.UserDto;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.dto.mapper.UserDtoMapper;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.UserService;
import com.sliwinski.exhibitions.service.utility.Search;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ExhibitionService exhibitionService;
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public AdminController(UserService userService, ExhibitionService exhibitionService, OrderService orderService, OrderDtoMapper orderDtoMapper, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.exhibitionService = exhibitionService;
        this.orderService = orderService;
        this.orderDtoMapper = orderDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @ModelAttribute("search")
    public Search newSearch() {
        return new Search();
    }

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
        Page<User> userPage = userService.getAllUsers(pageNumber-1);
        List<UserDto> users = userPage.getContent().stream()
                .map(userDtoMapper::toDto)
                .collect(toList());
        model.addAttribute("page", userPage.getTotalPages());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/orders")
    public String getOrders(@RequestParam(required = false) Integer page, Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        Page<Order> ordersPage = orderService.getAllOrders(pageNumber-1);
        List<OrderDto> orders = ordersPage.stream()
                .map(orderDtoMapper::toDto)
                .collect(toList());
        model.addAttribute("page", ordersPage.getTotalPages());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/exhibitions")
    public String getExhibitions(@ModelAttribute Search search,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) boolean resetFilter,
                                 Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        Page<Exhibition> exhibitionsPage;
        if(resetFilter) search = newSearch();
        if (search.getFrom() == null || search.getTo() == null) {
            exhibitionsPage = exhibitionService.getAllExhibitionsWithLocations(pageNumber - 1, search.getSort().direction());

        } else {
            exhibitionsPage = exhibitionService.searchAndSortExhibitionsWithLocations(
                            search.getFrom(), search.getTo(), pageNumber-1, search.getSort().direction(), search.getSort().field());

        }
        model.addAttribute("totalPages", exhibitionsPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("exhibitions", exhibitionsPage);
        model.addAttribute("page", pageNumber);
        return "exhibitions";
    }

    @PostMapping("/exhibitions")
    public String SearchExhibitions(@ModelAttribute Search search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/admin/exhibitions";
    }
}
