package com.sliwinski.exhibitions.controller;

import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.LocationService;
import com.sliwinski.exhibitions.service.validator.Validate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class LocationController {
    private final LocationService locationService;
    private final Validate validate;

    @GetMapping("/locations")
    public String getLocations(@RequestParam(required = false) Integer page, Model model) {
        int pageNumber = page != null && page > 0 ? page : 1;
        Page<Location> locationsPage = locationService.getAllLocations(pageNumber-1);
        model.addAttribute("page", locationsPage.getTotalPages());
        model.addAttribute("locations", locationsPage.getContent());
        return "locations";
    }

    @GetMapping("/locations/new")
    public String getAddLocation (Model model) {
        return "new-location";
    }

    @PostMapping("/locations/new")
    public String postAddLocation (@RequestParam String locationName, Model model) {
        validate.isFilled(locationName);
        Location location = new Location();
        location.setName(locationName);
        locationService.createLocation(location);
        return "redirect:/admin/locations";
    }
}
