package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
