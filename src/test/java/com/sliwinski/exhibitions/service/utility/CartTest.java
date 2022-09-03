package com.sliwinski.exhibitions.service.utility;

import com.sliwinski.exhibitions.dto.ExhibitionDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CartTest {
    private Cart cart = new Cart();
    private ExhibitionDto exhibitionDto1 = new ExhibitionDto(
            1,
            "Test 1",
            LocalDate.of(2022, 2, 1),
            LocalDate.of(2022,3,1),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            34.0);
    private ExhibitionDto exhibitionDto2 = new ExhibitionDto(
            2,
            "Test 2",
            LocalDate.of(2022, 4, 1),
            LocalDate.of(2022,5,1),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            30.0);
    private Item item1 = new Item(exhibitionDto1, 2, 68.0);
    private Item item2 = new Item(exhibitionDto2, 1, 30.0);

    @Test
    public void workflow() {
        cart.addItem(exhibitionDto1, 2);
        cart.addItem(exhibitionDto2, 1);

        assertIterableEquals(List.of(item1, item2), cart.getItems());
        assertEquals(3, cart.getQuantityTotal());
        assertEquals(98.0, cart.getPriceTotal());

        cart.removeItem(2);
        assertIterableEquals(List.of(item1), cart.getItems());

        cart.addItem(exhibitionDto2, 0);
        assertEquals(2, cart.getQuantityTotal());

        cart.updateQuantity();
        assertIterableEquals(List.of(item1), cart.getItems());
    }
}
