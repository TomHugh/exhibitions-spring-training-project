package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> checkAvailability(LocalDate startDate, LocalDate endDate) throws Exception {

        return locationRepository.findAll();
    }
}
