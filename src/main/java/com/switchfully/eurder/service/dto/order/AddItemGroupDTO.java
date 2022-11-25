package com.switchfully.eurder.service.dto.order;

public class AddItemGroupDTO {

    private long itemId;
    private int amount;

    public AddItemGroupDTO(long itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
