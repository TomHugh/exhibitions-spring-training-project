package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.UserDto;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.dto.mapper.UserDtoMapper;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.UserService;
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

    private Search currentSearch = new Search();

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
        List<UserDto> users = userService.getAllUsers(pageNumber-1).stream()
                .map(userDtoMapper::toDto)
                .collect(toList());
        model.addAttribute("page", userService.getTotalPages());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/orders")
    public String getOrders(@RequestParam(required = false) Integer page, Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        List<OrderDto> orders = orderService.getAllOrders(pageNumber-1).stream()
                .map(orderDtoMapper::toDto)
                .collect(toList());
        model.addAttribute("page", orderService.getTotalPages());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/exhibitions")
    public String getExhibitions(@ModelAttribute Search search,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) boolean resetFilter,
                                 Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        List<Exhibition> exhibitions;
        int totalPages;
        if(resetFilter) currentSearch = new Search();
        if(search.getFrom() != null && search.getTo() != null) currentSearch = search;
        if (currentSearch.getFrom() == null || currentSearch.getTo() == null) {
            exhibitions = exhibitionService.getAllExhibitionsWithLocations(pageNumber - 1, currentSearch.getSort().direction())
                    .stream()
                    .collect(toList());
        } else {
            exhibitions = exhibitionService.searchAndSortExhibitionsWithLocations(
                            currentSearch.getFrom(), currentSearch.getTo(), pageNumber-1, currentSearch.getSort().direction(), currentSearch.getSort().field())
                    .stream()
                    .collect(toList());

        }
        totalPages = exhibitionService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("search", currentSearch);
        model.addAttribute("exhibitions", exhibitions);
        model.addAttribute("page", pageNumber);
        return "exhibitions";
    }

    @PostMapping("/exhibitions")
    public String SearchExhibitions(@ModelAttribute Search search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/admin/exhibitions";
    }

    private class Search {
        private LocalDate from;
        private LocalDate to;
        private SortType sort = SortType.DATE_ASC;

        public LocalDate getFrom() {
            return from;
        }

        public void setFrom(LocalDate from) {
            this.from = from;
        }

        public LocalDate getTo() {
            return to;
        }

        public void setTo(LocalDate to) {
            this.to = to;
        }

        public SortType getSort() {
            return sort;
        }

        public void setSort(SortType sort) {
            this.sort = sort;
        }
    }
}
