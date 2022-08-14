package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.LocationService;
import com.sliwinski.exhibitions.service.utility.DatesLocations;
import com.sliwinski.exhibitions.service.utility.Search;
import com.sliwinski.exhibitions.service.validator.Validate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@SessionAttributes("search")
@RequestMapping("/admin")
public class ExhibitionController {
    private final ExhibitionService exhibitionService;
    private final LocationService locationService;

    @ModelAttribute("search")
    public Search newSearch() {
        return new Search();
    }

    @GetMapping("/exhibitions")
    public String getExhibitions(@ModelAttribute Search search,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) boolean resetFilter,
                                 Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        if(resetFilter) search = newSearch();
        Page<Exhibition> exhibitionsPage = exhibitionService.searchAndSortExhibitionsWithLocations(
                    search.getFrom(), search.getTo(), pageNumber-1, search.getSort().direction(), search.getSort().field());
        model.addAttribute("totalPages", exhibitionsPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("exhibitions", exhibitionsPage.getContent());
        model.addAttribute("page", pageNumber);
        return "exhibitions";
    }

    @PostMapping("/exhibitions")
    public String SearchExhibitions(@ModelAttribute Search search, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/admin/exhibitions";
    }


    @GetMapping("/new-exhibition/check-locations")
    public String getCheckLocations(Model model) {
        model.addAttribute("datesLocations", new DatesLocations());
        return "check-locations";
    }

    @PostMapping("/new-exhibition/check-locations")
    public String postCheckLocations(@ModelAttribute DatesLocations datesLocations, Model model, RedirectAttributes redirectAttributes) {
        try {
            Validate.startEndDates(datesLocations.getStartDate(), datesLocations.getEndDate());
            datesLocations.setLocations(locationService.checkAvailability(datesLocations.getStartDate(), datesLocations.getEndDate()));
        } catch (Exception e) {
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", e.getMessage());
            return "check-locations";
        }
        redirectAttributes.addFlashAttribute("datesLocations", datesLocations);
        return "redirect:/admin/new-exhibition/details";
    }

    @GetMapping("/new-exhibition/details")
    public String getDetails(@ModelAttribute DatesLocations datesLocations, Model model) {
        Exhibition exhibition = new Exhibition();
        exhibition.setStartDate(datesLocations.getStartDate());
        exhibition.setEndDate(datesLocations.getEndDate());
        model.addAttribute("exhibition", exhibition);
        model.addAttribute("locations", datesLocations.getLocations());
        return "details";
    }

    @PostMapping("/new-exhibition/details")
    public String postDetails(@ModelAttribute Exhibition exhibition, RedirectAttributes redirectAttributes) {
        exhibitionService.createExhibition(exhibition);
        redirectAttributes.addFlashAttribute("class", "alert-success");
        redirectAttributes.addFlashAttribute("message", "exhibition_created");
        return "redirect:/admin";
    }

    @GetMapping("/new-exhibition")
    public String getAddExhibitionView(Model model) {
        return "redirect:/admin/new-exhibition/check-locations";
    }

}
