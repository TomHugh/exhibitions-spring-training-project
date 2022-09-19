package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.dto.ExhibitionDetailsDto;
import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.utility.Cart;
import com.sliwinski.exhibitions.service.utility.Search;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Consists of main views for user.
 * Placing order by user is separated at OrderController.
 */

@Controller
@AllArgsConstructor
@RequestMapping
@SessionAttributes({"search", "cart"})
public class HomeController {

    private final ExhibitionService exhibitionService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @ModelAttribute("search")
    public Search newSearch() {
        return new Search();
    }

    @ModelAttribute("cart")
    public Cart newCart() {
        return new Cart();
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
        if(resetFilter) search = newSearch();
        Page<Exhibition> exhibitionsPage = exhibitionService.searchAndSortActiveExhibitions(
                    search.getFrom(), search.getTo(), pageNumber-1,
                    search.getSort().direction(), search.getSort().field());
        model.addAttribute("totalPages", exhibitionsPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("exhibitions", exhibitionsPage.getContent());
        model.addAttribute("page", pageNumber);
        return "home";
    }

    @PostMapping("/exhibitions")
    public String SearchExhibitions(@ModelAttribute Search search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/exhibitions";
    }

    @GetMapping("/exhibitions/{id}")
    public String getExhibition(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        authService.addUsernameAttribute(model);
        try {
            model.addAttribute("exhibition", modelMapper.map(exhibitionService.getExhibition(id), ExhibitionDetailsDto.class));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("class", "alert-danger");
            redirectAttributes.addFlashAttribute("message", "exhibition_not_found");
            return "redirect:/exhibitions";
        }
        return "exhibition";
    }

    @GetMapping("/cart/add")
    public String addToCart(@RequestParam int id, @RequestParam int quantity, @ModelAttribute Cart cart, RedirectAttributes redirectAttributes) throws Exception {
        ExhibitionDto exhibitionDto = modelMapper.map(exhibitionService.findExhibition(id), ExhibitionDto.class);
        cart.addItem(exhibitionDto, quantity);
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "added_to_cart");
        return "redirect:/exhibitions";
    }

    @GetMapping("/cart/remove")
    public String removeFromCart(@RequestParam int id, @ModelAttribute Cart cart, RedirectAttributes redirectAttributes) throws Exception {
        cart.removeItem(id);
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "removed_from_cart");
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@ModelAttribute Cart cart, RedirectAttributes redirectAttributes) throws Exception {
        cart.updateQuantity();
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "cart_updated");
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String getCart(@ModelAttribute Cart cart, Model model) {
        authService.addUsernameAttribute(model);
        model.addAttribute("totalQuantity", cart.getQuantityTotal());
        model.addAttribute("totalPrice", cart.getPriceTotal());
        return "cart";
    }
}
