package com.sliwinski.exhibitions.service.utility;


import com.sliwinski.exhibitions.dto.ExhibitionDto;

import java.util.Objects;

public class Item {
    private ExhibitionDto exhibitionDto;
    private int quantity;
    private double price;

    public Item(ExhibitionDto exhibitionDto, int quantity, double price) {
        this.exhibitionDto = exhibitionDto;
        this.quantity = quantity;
        this.price = price;
    }

    public ExhibitionDto getExhibitionDto() {
        return exhibitionDto;
    }

    public void setExhibitionDto(ExhibitionDto exhibitionDto) {
        this.exhibitionDto = exhibitionDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getQuantity() == item.getQuantity() && Double.compare(item.getPrice(), getPrice()) == 0 && getExhibitionDto().equals(item.getExhibitionDto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExhibitionDto(), getQuantity(), getPrice());
    }
}
