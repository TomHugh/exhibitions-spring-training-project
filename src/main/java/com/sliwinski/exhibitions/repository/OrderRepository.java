package com.sliwinski.exhibitions.repository;

import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    long count();

    Page<Order> findAll(Pageable page);

    @Query("select sum(e.ticketPrice) from Order o join o.exhibition as e")
    double getProfit();

    long countByExhibitionId(int id);

    Page<Order> findAllByExhibitionId(int exhibitionId, Pageable page);

    @Query("select o from Order o join fetch o.exhibition where o.user = :user")
    List<Order> findByUserFetchExhibition(User user);
}
