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

    public List<Exhibition> getAllExhibitons() {
        return exhibitionRepository.findAll();
    }
}
