package com.sliwinski.exhibitions.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ExhibitionDto {
    private int id;
    private String theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private float ticketPrice;
}
