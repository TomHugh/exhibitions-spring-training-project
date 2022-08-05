package com.sliwinski.exhibitions.service.checker;

import com.sliwinski.exhibitions.entity.Location;

import java.time.LocalDate;
import java.util.List;

public class Check {
    public static List<Location> locations(String startDate, String endDate) throws Exception {

    return List.of(new Location(6, "hardcoded 1"), new Location(7, "hardcoded 2"));
    }
}
