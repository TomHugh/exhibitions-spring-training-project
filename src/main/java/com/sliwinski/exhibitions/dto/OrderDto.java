package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private long id;
    private LocalDateTime timestamp;
    private String exhibitionTheme;
}
