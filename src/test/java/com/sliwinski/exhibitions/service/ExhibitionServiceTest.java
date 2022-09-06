package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.repository.ExhibitionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ExhibitionServiceTest {

    private static final Exhibition EXHIBITION = new Exhibition(
            1,
            "Test exhibition",
            LocalDate.of(2022, 3, 1),
            LocalDate.of(2022, 4, 1),
            LocalTime.of(10, 0),
            LocalTime.of(20, 0),
            35.0,
            true,
            new ArrayList<>(Collections.singleton(new Location(1, "TEST LOCATION"))));
    private static final List<Exhibition> EXHIBITION_LIST = new ArrayList<>(Collections.singleton(EXHIBITION));
    private static final Page<Exhibition> EXHIBITION_PAGE = new PageImpl<>(EXHIBITION_LIST);


    @Mock
    private ExhibitionRepository exhibitionRepository;

    @InjectMocks
    private ExhibitionService exhibitionService;

    @Test
    public void getQuantity() {
        Mockito.when(exhibitionRepository.countByIsActiveIs(true)).thenReturn(12L);
        assertEquals(12, exhibitionService.getQuantity());
    }

    @Test
    public void searchAndSortExhibitions() {
        Mockito.when(exhibitionRepository.findByStartDateBetween(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(PageRequest.class))).thenReturn(EXHIBITION_PAGE);
        assertEquals(EXHIBITION_PAGE, exhibitionService.searchAndSortExhibitions(
                LocalDate.of(2022,2,1),
                LocalDate.of(2022,3,1),
                1, Sort.Direction.ASC, "theme"));

        Mockito.when(exhibitionRepository.findByStartDateBetweenAndIsActive(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(PageRequest.class))).thenReturn(EXHIBITION_PAGE);
        assertEquals(EXHIBITION_PAGE, exhibitionService.searchAndSortActiveExhibitions(
                LocalDate.of(2022,2,1),
                LocalDate.of(2022,3,1),
                1, Sort.Direction.ASC, "theme"));
    }

    @Test
    public void createExhibition() {
        exhibitionService.createExhibition(EXHIBITION);
        ArgumentCaptor<Exhibition> exhibitionArgumentCaptor = ArgumentCaptor.forClass(Exhibition.class);
        Mockito.verify(exhibitionRepository).save(exhibitionArgumentCaptor.capture());
        Exhibition exhibition = exhibitionArgumentCaptor.getValue();

        assertEquals(1, exhibition.getId());
    }

    @Test
    public void getExhibition() {
        Mockito.when(exhibitionRepository.findByIdFetchLocations(Mockito.anyInt())).thenReturn(Optional.of(EXHIBITION));
        assertEquals(EXHIBITION, exhibitionService.getExhibition(1));
    }

    @Test
    public void findExhibition() {
        Mockito.when(exhibitionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EXHIBITION));
        assertEquals(EXHIBITION, exhibitionService.findExhibition(1));
    }

    @Test
    public void cancelExhibition() {
        Mockito.when(exhibitionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(EXHIBITION));
        exhibitionService.cancelExhibition(1);
        assertFalse(EXHIBITION.isActive());
    }

}
