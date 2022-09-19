package com.sliwinski.exhibitions.service.utility;

import com.sliwinski.exhibitions.entity.Location;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for storing start date, end date and locations of exhibition. Used at first step of creation of exhibition to provide free locations for given dates.
 */

public class DatesLocations {
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
