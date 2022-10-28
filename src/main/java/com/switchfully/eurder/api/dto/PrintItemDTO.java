package com.switchfully.eurder.api.dto;

public class PrintItemDTO {

    private String id;
    private String name;
    private String description;
    private double price;
    private int amount;

    public PrintItemDTO setID(String id){
        this.id = id;
        return this;
    }

    public PrintItemDTO setName(String name){
        this.name = name;
        return this;
    }
    public PrintItemDTO setDescription(String description){
        this.description = description;
        return this;
    }
    public PrintItemDTO setPrice(double price){
        this.price = price;
        return this;
    }

    public PrintItemDTO setAmount(int amount){
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

    public String getId() {
        return id;
    }
}
