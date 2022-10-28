package com.switchfully.eurder.api.dto;

public class AddItemGroupDTO {
    private String itemId;
    private int amount;

    public AddItemGroupDTO setItemId(String itemId){
        this.itemId = itemId;
        return this;
    }

    public AddItemGroupDTO setAmount(int amount){
        this.amount = amount;
        return this;
    }


    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
