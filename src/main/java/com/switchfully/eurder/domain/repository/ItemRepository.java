package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    public LocalDate calculateShippingDate(String itemId, int amount) {
        if (isInStock(itemId, amount)) return LocalDate.now().plusDays(1);
        return LocalDate.now().plusDays(7);
    }

    public double getItemPrice(String itemId){
        return items.get(itemId).getPrice();
    }

    public List<Item> getItems() {
        return items.values().stream().toList();
    }

    public Item getItem(String id){return items.get(id);}

    private boolean isInStock(String itemId, int amount) {
        return items.get(itemId).getAmount() > amount;
    }

    public Item getItemByName(String name){
        return items.values().stream().filter(item->item.getName().equals(name)).findFirst().orElseThrow();
    }

    private void setupDataBase(){
        Item screw = new Item("Screw", "Something to fix wood to stuff with", 0.5, 10);
        Item wood = new Item("Wood", "Something that can be fixed to other stuff by screws", 100, 2);
        addNewItem(screw);
        addNewItem(wood);
    }

    public void updateItem(String id, Item updatedItem) {
        items.put(id, updatedItem);
    }

    public boolean itemIsInStock(String itemId, int orderAmount) {
        return items.get(itemId).getAmount() >= orderAmount;
    }
}
