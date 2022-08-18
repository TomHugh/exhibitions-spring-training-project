package com.sliwinski.exhibitions.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetailsDto {
    private long id;
    private LocalDateTime timestamp;
    private long userId;
    private String exhibitionTheme;
}
