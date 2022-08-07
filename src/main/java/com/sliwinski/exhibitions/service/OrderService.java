package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.dto.mapper.OrderDtoMapper;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.OrderRepository;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final AuthService authService;
    private final OrderDtoMapper orderDtoMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ExhibitionRepository exhibitionRepository, AuthService authService, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.authService = authService;
        this.orderDtoMapper = orderDtoMapper;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDto> getUserOrders() {
        User user = authService.getUser();
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderDtoMapper::toDto)
                .collect(toList());
    }

    public void createOrder(int exhibitionId) {
        Order order = new Order();
        User user = authService.getUser();
        order.setUser(user);
        order.setExhibition(exhibitionRepository.findById(exhibitionId));
        orderRepository.save(order);
    }
}
