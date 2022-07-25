package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Integer> {
}
