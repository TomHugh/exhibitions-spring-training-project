package com.sliwinski.exhibitions.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionDto {
    private int id;
    private String theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private double ticketPrice;
}
