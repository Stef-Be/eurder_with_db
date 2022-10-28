package com.switchfully.eurder.api.dto.item;

public class UpdatedItemDTO {
    private String name;
    private String description;
    private double price;
    private int amount;

    public UpdatedItemDTO setName(String name){
        this.name = name;
        return this;
    }
    public UpdatedItemDTO setDescription(String description){
        this.description = description;
        return this;
    }
    public UpdatedItemDTO setPrice(double price){
        this.price = price;
        return this;
    }

    public UpdatedItemDTO setAmount(int amount){
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
