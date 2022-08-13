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
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public Long getQuantity() {
        return  exhibitionRepository.count();
    }

    public Page<Exhibition> getAllExhibitions(int page, Sort.Direction direction) {
        return exhibitionRepository.findAllPageable(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "startDate")));
    }

    public Page<Exhibition> searchAndSortExhibitions(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        return exhibitionRepository.findByStartDateBetween(from, to, PageRequest.of(page, PAGE_SIZE, Sort.by(direction, field)));
    }

    public Page<Exhibition> getAllExhibitionsWithLocations(int page, Sort.Direction direction) {
        return exhibitionRepository.findAllFetchLocationsPageable(PageRequest.of(page, ADMIN_PAGE_SIZE, Sort.by(direction, "startDate")));
    }
    public Page<Exhibition> searchAndSortExhibitionsWithLocations(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        return exhibitionRepository.findByStartDateBetweenFetchLocations(from, to, PageRequest.of(page, ADMIN_PAGE_SIZE, Sort.by(direction, field)));
    }

    public List<Location> getExhibitionLocations(int exhibitionId) {
        return exhibitionRepository.getExhibitionLocations(exhibitionId);
    }

    public void createExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }

    public Exhibition getExhibition(int exhibitionId) throws Exception {
        return exhibitionRepository.findByIdFetchLocations(exhibitionId).orElseThrow(() -> new RuntimeException("Exhibition not found"));
    }
}