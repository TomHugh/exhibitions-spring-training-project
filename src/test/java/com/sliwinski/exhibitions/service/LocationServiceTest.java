package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import com.sliwinski.exhibitions.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LocationServiceTest {
    private static final Location LOCATION1 = new Location(1, "Location 1");
    private static final Location LOCATION2 = new Location(2, "Location 2");
    private static final Location LOCATION3 = new Location(3, "Location 3");


    private List<Location> allLocations = new ArrayList<>(Arrays.asList(LOCATION1, LOCATION2, LOCATION3));
    private Set<Location> occupiedLocations = new HashSet<>(Arrays.asList(LOCATION1, LOCATION2));

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ExhibitionRepository exhibitionRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void checkAvailability() {
        Mockito.when(locationRepository.findAll()).thenReturn(allLocations);
        Mockito.when(exhibitionRepository.findOccupiedLocations(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class))).thenReturn(occupiedLocations);

        List<Location> expected = new ArrayList<>(Collections.singleton(LOCATION3));

        assertEquals(expected, locationService.checkAvailability(
                        LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 1)));
    }

    @Test
    public void createLocation() {
        locationService.createLocation(LOCATION1);
        ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        Mockito.verify(locationRepository).save(locationArgumentCaptor.capture());
        Location location = locationArgumentCaptor.getValue();

        assertEquals(1, location.getId());
    }

    @Test
    public void getAllLocations() {
        Page<Location> pageLocations = new PageImpl<>(allLocations);
        Mockito.when(locationRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(pageLocations);
        assertEquals(pageLocations, locationService.getAllLocations(1));
    }
}
