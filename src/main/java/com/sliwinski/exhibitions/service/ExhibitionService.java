package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExhibitionService {
    private final int PAGE_SIZE = 5;
    private final int ADMIN_PAGE_SIZE = 10;

    private final ExhibitionRepository exhibitionRepository;
    public ExhibitionService(ExhibitionRepository exhibitionRepository, LocationRepository locationRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }
    private int totalPages;
    public int getTotalPages() {
        return totalPages;
    }

    private Long quantity;
    public Long getQuantity() {
        if(quantity == null) quantity = exhibitionRepository.count();
        return quantity;
    }

    public Page<Exhibition> getAllExhibitions(int page, Sort.Direction direction) {
        Page<Exhibition> exhibitions = exhibitionRepository.findAllPageable(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "startDate")));
        totalPages = exhibitions.getTotalPages();
        return exhibitions;
    }

    public Page<Exhibition> searchAndSortExhibitions(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        Page<Exhibition> exhibitions = exhibitionRepository.findByStartDateBetween(from, to, PageRequest.of(page, PAGE_SIZE, Sort.by(direction, field)));
        totalPages = exhibitions.getTotalPages();
        return exhibitions;
    }

    public Page<Exhibition> getAllExhibitionsWithLocations(int page, Sort.Direction direction) {
        Page<Exhibition> exhibitions = exhibitionRepository.findAllFetchLocationsPageable(PageRequest.of(page, ADMIN_PAGE_SIZE, Sort.by(direction, "startDate")));
        totalPages = exhibitions.getTotalPages();
        return exhibitions;
    }
    public Page<Exhibition> searchAndSortExhibitionsWithLocations(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        Page<Exhibition> exhibitions = exhibitionRepository.findByStartDateBetweenFetchLocations(from, to, PageRequest.of(page, ADMIN_PAGE_SIZE, Sort.by(direction, field)));
        totalPages = exhibitions.getTotalPages();
        return exhibitions;
    }

    public List<Location> getExhibitionLocations(int exhibitionId) {
        return exhibitionRepository.getExhibitionLocations(exhibitionId);
    }

    public void createExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
        quantity++;
    }

    public Exhibition getExhibition(int exhibitionId) throws Exception {
        return exhibitionRepository.findByIdFetchLocations(exhibitionId).orElseThrow(() -> new RuntimeException("Exhibition not found"));
    }
}