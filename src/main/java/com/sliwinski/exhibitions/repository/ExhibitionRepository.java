package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
}
