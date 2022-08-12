package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final ExhibitionRepository exhibitionRepository;

    public LocationService(LocationRepository locationRepository, ExhibitionRepository exhibitionRepository) {
        this.locationRepository = locationRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Location> checkAvailability(LocalDate startDate, LocalDate endDate) throws Exception {
        List<Location> freeLocations = locationRepository.findAll();
        Set<Location> occupiedLocations = exhibitionRepository.findOccupiedLocations(startDate, endDate);
        freeLocations.removeAll(occupiedLocations);
        return freeLocations;
    }
}
