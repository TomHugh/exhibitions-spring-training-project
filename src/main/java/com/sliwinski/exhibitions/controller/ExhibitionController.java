package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.checker.Check;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ExhibitionController {


    @GetMapping("/new-exhibition/check-locations")
    public String getCheckLocations(Model model) {
        return "check-locations";
    }

    @PostMapping("/new-exhibition/check-locations")
    public String postCheckLocations(@RequestParam Map <String, String> body, Model model, RedirectAttributes redirectAttributes) {
        List<Location> locations;
        try {
            locations = Check.locations(body.get("startDate"), body.get("endDate"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        redirectAttributes.addFlashAttribute(body);
        redirectAttributes.addFlashAttribute("locations", locations);
        return "redirect:/admin/new-exhibition/choose-locations";
    }

    @GetMapping("/new-exhibition/choose-locations")
    public String getChooseLocations(Model model) {
        return "choose-locations";
    }

    @PostMapping("/new-exhibition/choose-locations")
    public String postChooseLocations(@RequestParam Map <String, String> body, @RequestParam List<String> locations, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(body);
        redirectAttributes.addFlashAttribute("locations", locations);
        return "redirect:/admin/new-exhibition/details";
    }

    @GetMapping("/new-exhibition/details")
    public String getDetails(Model model) {
        return "details";
    }


    @GetMapping("/new-exhibition")
    public String getAddExhibitionView(Model model) {
        return "redirect:/admin/new-exhibition/check-locations";
    }

}
