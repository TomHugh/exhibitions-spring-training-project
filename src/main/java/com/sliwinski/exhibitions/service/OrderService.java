package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.OrderRepository;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final int PAGE_SIZE = 10;

    private final OrderRepository orderRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final AuthService authService;

    public OrderService(OrderRepository orderRepository, ExhibitionRepository exhibitionRepository, AuthService authService) {
        this.orderRepository = orderRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.authService = authService;
    }

    public Long getQuantity() {
        return orderRepository.count();
    }

    public Page<Order> getAllOrders(int page) {
        return orderRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id")));
    }

    public List<Order> getUserOrders() {
        long userId = authService.getUser().getId();
        return orderRepository.findByUserId(userId);
    }

    public void createOrder(int exhibitionId) {
        Order order = new Order();
        User user = authService.getUser();
        order.setUser(user);
        order.setExhibition(exhibitionRepository.findById(exhibitionId).get());
        orderRepository.save(order);
    }

    public double getProfit() {
        return orderRepository.getProfit();
    }
}
