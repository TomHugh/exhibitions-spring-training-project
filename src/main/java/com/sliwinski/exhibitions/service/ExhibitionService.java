package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
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
        return  exhibitionRepository.countByIsActiveIs(true);
    }

    public Page<Exhibition> searchAndSortExhibitions(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        return exhibitionRepository.findByStartDateBetween(from, to, PageRequest.of(page, ADMIN_PAGE_SIZE, Sort.by(direction, field)));
    }

    public Page<Exhibition> searchAndSortActiveExhibitions(LocalDate from, LocalDate to, int page, Sort.Direction direction, String field) {
        return exhibitionRepository.findByStartDateBetweenAndIsActive(from, to, PageRequest.of(page, PAGE_SIZE, Sort.by(direction, field)));
    }

    public void createExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }

    public Exhibition getExhibition(int exhibitionId) throws Exception {
        return exhibitionRepository.findByIdFetchLocations(exhibitionId).orElseThrow(() -> new RuntimeException("Exhibition not found"));
    }

    public Exhibition findExhibition(int exhibitionId) throws Exception{
        return exhibitionRepository.findById(exhibitionId).orElseThrow(() -> new RuntimeException("Exhibition not found"));
    }

    public void cancelExhibition(int id) {
        exhibitionRepository.cancelById(id);
    }

}