package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {
    private static final Order ORDER1 = new Order(
            1,
            LocalDateTime.of(2022, 2, 1, 12, 12),
            null,
            null);
    private static final Order ORDER2 = new Order(
            1,
            LocalDateTime.of(2022, 3, 1, 13, 13),
            null,
            null);

    private List<Order> listOrders = new ArrayList<>(Arrays.asList(ORDER1, ORDER2));
    private Page<Order> pageOrders = new PageImpl<>(listOrders);

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getQuantity() {
        Mockito.when(orderRepository.count()).thenReturn(10L);
        assertEquals(10, orderService.getQuantity());
    }

    @Test
    public void getAllOrders() {
        Mockito.when(orderRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(pageOrders);
        assertEquals(pageOrders, orderService.getAllOrders(1));
    }

    @Test
    public void getUserOrders() {
        Mockito.when(authService.getUser()).thenReturn(null);
        Mockito.when(orderRepository.findByUserFetchExhibition(null)).thenReturn(listOrders);
        assertEquals(listOrders, orderService.getUserOrders());
    }

    @Test
    public void getProfit() {
        Mockito.when(orderRepository.getProfit()).thenReturn(10.0);
        assertEquals(10.0, orderService.getProfit());
    }

    @Test
    public void countByExhibitionId() {
        Mockito.when(orderRepository.countByExhibitionId(Mockito.anyInt())).thenReturn(10L);
        assertEquals(10L, orderService.countByExhibitionId(1));
    }

    @Test
    public void getByExhibitionId() {
        Mockito.when(orderRepository.findAllByExhibitionId(Mockito.anyInt(),
                Mockito.any(PageRequest.class))).thenReturn(pageOrders);
        assertEquals(pageOrders, orderService.getByExhibitionId(1, 1));
    }

}
