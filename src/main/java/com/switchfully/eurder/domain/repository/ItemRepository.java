package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.api.dto.AddOrderDTO;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemRepository {
    private Map<String, Item> items;

    public ItemRepository() {
        this.items = new HashMap<>();
        setupDataBase();
    }

    public void addNewItem(Item itemToAdd) {
        items.put(itemToAdd.getId(), itemToAdd);
    }

    public LocalDate calculateShippingDate(String itemId) {
        if (isInStock(itemId)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    public double getItemPrice(String itemId){
        return items.get(itemId).getPrice();
    }

    private boolean isInStock(String itemId) {
        return items.get(itemId).getAmount() > 0;
    }

    private void setupDataBase(){
        Item screw = new Item("Screw", "Something to fix wood to stuff with", 0.5, 10);
        Item wood = new Item("Wood", "Something that can be fixed to other stuff by screws", 100, 2);
        addNewItem(screw);
        addNewItem(wood);
        System.out.println(screw.getId());
        System.out.println(wood.getId());
    }
}