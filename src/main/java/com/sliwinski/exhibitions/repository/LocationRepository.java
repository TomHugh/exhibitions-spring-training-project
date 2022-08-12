package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {


}
