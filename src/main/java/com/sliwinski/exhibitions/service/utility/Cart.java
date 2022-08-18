package com.sliwinski.exhibitions.service.utility;


import com.sliwinski.exhibitions.dto.ExhibitionDto;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(ExhibitionDto exhibitionDto, int quantity) {
        Item item = this.findItemById(exhibitionDto.getId());

        if(item == null) {
            item = new Item(exhibitionDto, quantity, quantity * exhibitionDto.getTicketPrice());
        } else {
            int newQuantity = item.getQuantity() + quantity;
            item.setQuantity(newQuantity);
        }
        items.add(item);
    }

    private Item findItemById(int id) {
        for (Item item : this.items) {
            if (item.getExhibitionDto().getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void updateItem(int id, int quantity) {
        Item item = this.findItemById(id);
        if (item != null) {
            if (quantity <= 0) {
                this.items.remove(item);
            } else {
                item.setQuantity(quantity);
                item.setPrice(quantity * item.getExhibitionDto().getTicketPrice());
            }
        }
    }

    public void removeItem(int id) {
        Item item = this.findItemById(id);
        if (item != null) {
            this.items.remove(item);
        }
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (Item item : this.items) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    public double getPriceTotal() {
        double total = 0;
        for (Item item : this.items) {
            total += item.getPrice();
        }
        return total;
    }

    public void updateQuantity() {
            List<Item> listToIterate = new ArrayList<>(this.items);
            for (Item item : listToIterate) {
                this.updateItem(item.getExhibitionDto().getId(), item.getQuantity());
            }
    }

}
