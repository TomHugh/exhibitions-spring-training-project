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

import java.time.LocalDate;
import java.util.List;

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

    private class DatesLocations {
        private LocalDate startDate;
        private LocalDate endDate;
        private List<Location> locations;

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public List<Location> getLocations() {
            return locations;
        }

        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }
    }

}
