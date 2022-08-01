package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {

    @Override
    @Query("select distinct e from Exhibition e join fetch e.locations")
    List<Exhibition> findAll();
}
