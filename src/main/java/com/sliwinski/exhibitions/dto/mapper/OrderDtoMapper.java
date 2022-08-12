package com.sliwinski.exhibitions.dto.mapper;

import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .timestamp(order.getTimestamp())
                .build();
    }
}
