package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemRepository {
    private Map<String, Item> items;

    public ItemRepository() {
        this.items = new HashMap<>();
    }

    public void addNewItem(Item itemToAdd) {
        items.put(itemToAdd.getId(), itemToAdd);
    }

    public LocalDate calculateShippingDate(String itemId) {
        if (isInStock(itemId)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    private boolean isInStock(String itemId) {
        return items.get(itemId).getAmount() > 0;
    }
}
