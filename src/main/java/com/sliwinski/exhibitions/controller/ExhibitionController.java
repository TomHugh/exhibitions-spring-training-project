package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.LocationService;
import com.sliwinski.exhibitions.service.validator.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ExhibitionController {
    private final ExhibitionService exhibitionService;
    private final LocationService locationService;

    public ExhibitionController(ExhibitionService exhibitionService, LocationService locationService) {
        this.exhibitionService = exhibitionService;
        this.locationService = locationService;
    }


    @GetMapping("/new-exhibition/check-locations")
    public String getCheckLocations(Model model) {
        return "check-locations";
    }

    @PostMapping("/new-exhibition/check-locations")
    public String postCheckLocations(@RequestParam Map <String, String> dates, Model model, RedirectAttributes redirectAttributes) {
        LocalDate startDate = LocalDate.parse(dates.get("startDate"));
        LocalDate endDate = LocalDate.parse(dates.get("endDate"));
        List<Location> chooseLocations;
        try {
            Validate.startEndDates(startDate, endDate);
            chooseLocations = locationService.checkAvailability(LocalDate.parse(dates.get("startDate")), LocalDate.parse(dates.get("endDate")));
        } catch (Exception e) {
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", e.getMessage());
            return "check-locations";
        }

        redirectAttributes.addFlashAttribute("startDate", dates.get("startDate"));
        redirectAttributes.addFlashAttribute("endDate", dates.get("endDate"));
        redirectAttributes.addFlashAttribute("chooseLocations", chooseLocations);
        return "redirect:/admin/new-exhibition/details";
    }

    @GetMapping("/new-exhibition/details")
    public String getDetails(Model model) {
        List<Location> locations = new ArrayList<>();
        model.addAttribute("locations", locations);
        return "details";
    }

    @PostMapping("/new-exhibition/details")
    public String postDetails(Exhibition exhibition, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//        String[] locations = request.getParameterValues("location");
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
