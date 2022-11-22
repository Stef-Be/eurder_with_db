package com.switchfully.eurder.service.dto.order;

public class AddItemGroupDTO {
    private long itemId;
    private int amount;

    public AddItemGroupDTO setItemId(long itemId){
        this.itemId = itemId;
        return this;
    }

    public AddItemGroupDTO setAmount(int amount){
        this.amount = amount;
        return this;
    }


    public long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
