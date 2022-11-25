package com.switchfully.eurder.service.dto.order;

public class PrintAllItemGroupsDTO {
    private String itemName;
    private int amount;
    private double totalPrice;

    public PrintAllItemGroupsDTO(String itemName, int amount, double totalPrice) {
        this.itemName = itemName;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
