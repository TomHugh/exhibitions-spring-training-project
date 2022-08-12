package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.mapper.ExhibitionDtoMapper;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@RequestMapping
public class HomeController {

    private final ExhibitionService exhibitionService;
    private final AuthService authService;
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;
    private final ExhibitionDtoMapper exhibitionDtoMapper;

    public HomeController(ExhibitionService exhibitionService, AuthService authService, OrderService orderService, OrderDtoMapper orderDtoMapper, OrderDtoMapper orderDtoMapper1, ExhibitionDtoMapper exhibitionDtoMapper) {
        this.exhibitionService = exhibitionService;
        this.authService = authService;
        this.orderService = orderService;
        this.orderDtoMapper = orderDtoMapper;
        this.exhibitionDtoMapper = exhibitionDtoMapper;
    }

    private Search currentSearch = new Search();

    @GetMapping("/")
    public String getHome() {
        return "redirect:/exhibitions?page=1";
    }
    @GetMapping("/exhibitions")
    public String getExhibitions(@ModelAttribute Search search,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) boolean resetFilter,
                                 Model model) {
        authService.addUsernameAttribute(model);
        int pageNumber = page != null && page > 0 ? page : 1;
        List<ExhibitionDto> exhibitions;
        int totalPages;
        if(resetFilter) currentSearch = new Search();
        if(search.getFrom() != null && search.getTo() != null) currentSearch = search;
        if (currentSearch.getFrom() == null || currentSearch.getTo() == null) {
            exhibitions = exhibitionService.getAllExhibitions(pageNumber - 1, currentSearch.getSort().direction()).stream()
                    .map(exhibitionDtoMapper::toDto)
                    .collect(toList());
        } else {
            exhibitions = exhibitionService.searchAndSortExhibitions(
                    currentSearch.getFrom(), currentSearch.getTo(), pageNumber-1, currentSearch.getSort().direction(), currentSearch.getSort().field())
                    .stream()
                    .map(exhibitionDtoMapper::toDto)
                    .collect(toList());
        }
        totalPages = exhibitionService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("search", currentSearch);
        model.addAttribute("exhibitions", exhibitions);
        model.addAttribute("page", pageNumber);
        return "home";
    }

    @PostMapping("/exhibitions")
    public String SearchExhibitions(@ModelAttribute Search search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/exhibitions";
    }

    @GetMapping("/exhibitions/{exhibitionId}")
    public String getExhibition(@PathVariable int exhibitionId, Model model, RedirectAttributes redirectAttributes) {
        authService.addUsernameAttribute(model);
        try {
            model.addAttribute("exhibition", exhibitionService.getExhibition(exhibitionId));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("class", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "exhibition_not_found");
            return "redirect:/exhibitions";
        }
        return "exhibition";
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
