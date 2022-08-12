package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderDto {
    private long id;
    private LocalDateTime timestamp;
}
