package com.sliwinski.exhibitions.service.utility;


import com.sliwinski.exhibitions.dto.ExhibitionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionalities to choose tickets by user.
 */

public class Cart {
    private final List<Item> items = new ArrayList<>();

    /**
     * @return List of items in the cart
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Adds item to the cart
     * @param exhibitionDto
     * @param quantity
     */
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

    /**
     * Finds item in the cart
     * @param id
     * @return Item or null if item not found
     */
    private Item findItemById(int id) {
        for (Item item : this.items) {
            if (item.getExhibitionDto().getId() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Removes item from the cart
     * @param id
     */
    public void removeItem(int id) {
        Item item = this.findItemById(id);
        if (item != null) {
            this.items.remove(item);
        }
    }

    /**
     * @return Total quantity of items (tickets) in the cart
     */
    public int getQuantityTotal() {
        int quantity = 0;
        for (Item item : this.items) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    /**
     * @return Total cart price
     */
    public double getPriceTotal() {
        double total = 0;
        for (Item item : this.items) {
            total += item.getPrice();
        }
        return total;
    }

    /**
     * Updates quantity and price of the items in the cart after changes made by user.
     */
    public void updateQuantity() {
            for (int i=0; i<this.items.size(); i++) {
                Item item = this.items.get(i);
                if (item.getQuantity() <= 0) {
                    this.items.remove(item);
                } else {
                    item.setPrice(item.getQuantity() * item.getExhibitionDto().getTicketPrice());
                }
            }
    }
}
