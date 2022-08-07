package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(long id);
}
