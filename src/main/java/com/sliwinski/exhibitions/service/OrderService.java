package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.OrderRepository;
import com.sliwinski.exhibitions.service.utility.Cart;
import com.sliwinski.exhibitions.service.utility.Item;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final int PAGE_SIZE = 10;

    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public Long getQuantity() {
        return orderRepository.count();
    }

    public Page<Order> getAllOrders(int page) {
        return orderRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id")));
    }

    public List<Order> getUserOrders() {
        User user = authService.getUser();
        return orderRepository.findByUserFetchExhibition(user);
    }

    public double getProfit() {
        return orderRepository.getProfit();
    }

    public long countByExhibitionId(int id) {
        return orderRepository.countByExhibitionId(id);
    }

    public Page<Order> getByExhibitionId(int exhibitionId, int page) {
        return orderRepository.findAllByExhibitionId(exhibitionId, PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id")));
    }

    @Transactional
    public void saveCart(Cart cart) {
        List<Item> items = cart.getItems();
        for(Item item : items) {
            for(int i=0; i<item.getQuantity(); i++) {
                Order order = new Order();
                User user = authService.getUser();
                order.setUser(user);
                order.setExhibition(modelMapper.map(item.getExhibitionDto(), Exhibition.class));
                orderRepository.save(order);
            }
        }
    }
}
