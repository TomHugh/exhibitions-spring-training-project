package com.sliwinski.exhibitions.service.validator;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.exception.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValidateTest {

    @InjectMocks
    private Validate validate;

    @Test
    public void password() {
        assertTrue(validate.password("12345678", "12345678"));
        assertThrows(ShortPasswordException.class, () -> validate.password("1234", "1234"));
        assertThrows(WrongRetypedPasswordException.class, () -> validate.password("12345678", "87654321"));
    }

    @Test
    public void startEndDates() {
        assertTrue(validate.startEndDates(LocalDate.of(2022, 2,1), LocalDate.now()));
        assertThrows(EmptyDateException.class, () -> validate.startEndDates(null, LocalDate.now()));
        assertThrows(EndBeforeStartException.class, () -> validate.startEndDates(LocalDate.now(), LocalDate.of(2022, 2,1)));
        assertThrows(EndBeforeNowException.class, () -> validate.startEndDates(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)));
    }

    @Test
    public void openingClosingHours() {
        assertTrue(validate.openingClosingHours(LocalTime.of(10, 0), LocalTime.of(18,0)));
        assertThrows(EmptyTimeException.class, () -> validate.openingClosingHours(null, LocalTime.of(18,0)));
        assertThrows(ClosingBeforeOpeningException.class, () -> validate.openingClosingHours(LocalTime.of(18,0), LocalTime.of(10,0)));
    }

    @Test
    public void isFilled() {
        assertTrue(validate.isFilled("filled"));
        assertThrows(EmptyFieldException.class, () -> validate.isFilled(""));
    }

    @Test
    public void exhibition() {
        Location location = new Location(1, "TEST LOCATION");
        Exhibition exhibition  = new Exhibition(
                1,
                "test",
                LocalDate.of(2022, 2, 1),
                LocalDate.now(),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                34.0,
                true,
                new ArrayList<>(Collections.singleton(location)));

        assertTrue(validate.exhibition(exhibition));

        exhibition.setTicketPrice(-10.0);
        assertThrows(BadTicketPriceException.class, () -> validate.exhibition(exhibition));

        exhibition.setTheme("");
        assertThrows(EmptyThemeException.class, () -> validate.exhibition(exhibition));

        exhibition.setLocations(Collections.emptyList());
        assertThrows(EmptyLocationsException.class, () -> validate.exhibition(exhibition));
    }
}