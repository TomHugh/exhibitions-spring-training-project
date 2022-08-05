package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.OrderRepository;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ExhibitionRepository exhibitionRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ExhibitionRepository exhibitionRepository) {
        this.orderRepository = orderRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void createOrder(int exhibitionId) {
        Order order = new Order();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUser(user);
        order.setExhibition(exhibitionRepository.findById(exhibitionId));
        orderRepository.save(order);
    }
}
