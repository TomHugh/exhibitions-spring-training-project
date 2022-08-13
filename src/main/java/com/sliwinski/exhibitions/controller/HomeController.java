package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.mapper.ExhibitionDtoMapper;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.utility.Search;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@RequestMapping
@SessionAttributes("search")
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

    @ModelAttribute("search")
    public Search newSearch() {
        return new Search();
    }

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
        Page<Exhibition> exhibitionPage;
        if(resetFilter) search = newSearch();
        if (search.getFrom() == null || search.getTo() == null) {
            exhibitionPage = exhibitionService.getAllExhibitions(pageNumber - 1, search.getSort().direction());
        } else {
            exhibitionPage = exhibitionService.searchAndSortExhibitions(
                    search.getFrom(), search.getTo(), pageNumber-1, search.getSort().direction(), search.getSort().field());
        }
        List<ExhibitionDto> exhibitions = exhibitionPage.stream()
                .map(exhibitionDtoMapper::toDto)
                .collect(toList());
        model.addAttribute("totalPages", exhibitionPage.getTotalPages());
        model.addAttribute("search", search);
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
        return "user-orders";
    }


}
