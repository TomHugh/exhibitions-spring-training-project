package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
