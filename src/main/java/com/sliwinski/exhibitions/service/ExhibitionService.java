package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    private int quantity = 0;

    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> exhibitions = exhibitionRepository.findAllDistinct();
        quantity = exhibitions.size();
        return exhibitions;
    }

    public int getQuantity() {
        return quantity;
    }

    public void createExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }
}