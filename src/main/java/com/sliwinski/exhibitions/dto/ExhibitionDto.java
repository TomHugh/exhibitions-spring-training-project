package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExhibitionDto {
    private int id;
    private String theme;
}
