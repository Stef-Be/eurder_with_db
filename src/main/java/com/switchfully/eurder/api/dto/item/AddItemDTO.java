package com.switchfully.eurder.api.dto.item;

public class AddItemDTO {
    private String name;
    private String description;
    private double price;
    private int amount;

    public AddItemDTO setName(String name){
        this.name = name;
        return this;
    }
    public AddItemDTO setDescription(String description){
        this.description = description;
        return this;
    }
    public AddItemDTO setPrice(double price){
        this.price = price;
        return this;
    }

    public AddItemDTO setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
