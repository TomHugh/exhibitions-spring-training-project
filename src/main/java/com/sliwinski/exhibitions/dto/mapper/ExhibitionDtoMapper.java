package com.sliwinski.exhibitions.dto.mapper;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.entity.Exhibition;
import org.springframework.stereotype.Component;

@Component
public class ExhibitionDtoMapper {
    public ExhibitionDto toDto(Exhibition exhibition) {
        return ExhibitionDto.builder()
                .id(exhibition.getId())
                .theme(exhibition.getTheme())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .openingHour(exhibition.getOpeningHour())
                .closingHour(exhibition.getClosingHour())
                .ticketPrice(exhibition.getTicketPrice())
                .build();
    }
}
