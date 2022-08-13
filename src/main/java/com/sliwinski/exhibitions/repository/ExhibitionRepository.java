package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
    long count();

    Exhibition findById(int exhibitionId);

    @Query("select e from Exhibition e")
    Page<Exhibition> findAllPageable(Pageable page);

    Page<Exhibition> findByStartDateBetween(LocalDate from, LocalDate to, Pageable page);

    @Query("select distinct e from Exhibition e join e.locations")
//    @Query("select distinct e from Exhibition e join fetch e.locations")
    Page<Exhibition> findAllFetchLocationsPageable(Pageable page);

    @Query("select distinct e from Exhibition e join e.locations where e.startDate between :from and :to")
//    @Query("select distinct e from Exhibition e join fetch e.locations where e.startDate between :from and :to") //this Query gives exception
    Page<Exhibition> findByStartDateBetweenFetchLocations(LocalDate from, LocalDate to, Pageable page);

    @Query("select e from Exhibition e join fetch e.locations where e.id = ?1")
    Optional<Exhibition> findByIdFetchLocations(int exhibitionId);

    @Query("select e.locations from Exhibition e where e.id = ?1")
    List<Location> getExhibitionLocations(int exhibitionId);

    @Query("select e.locations from Exhibition e where e.startDate < :endDate and e.endDate > :startDate")
    Set<Location> findOccupiedLocations(LocalDate startDate, LocalDate endDate);
}
