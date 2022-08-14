package com.sliwinski.exhibitions.dto.mapper;

import com.sliwinski.exhibitions.dto.LocationDto;
import com.sliwinski.exhibitions.dto.UserDto;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper {
    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .name(location.getName())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setName(locationDto.getName());
        return new Location();
    }
}
