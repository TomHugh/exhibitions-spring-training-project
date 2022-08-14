package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    long count();

    List<Order> findByUserId(long userId);

    Page<Order> findAll(Pageable page);

    @Query("select sum(e.ticketPrice) from Order o join o.exhibition as e")
    double getProfit();
}
