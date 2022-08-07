package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class OrderDto {
    private long id;
    private String exhibitionTheme;
}
