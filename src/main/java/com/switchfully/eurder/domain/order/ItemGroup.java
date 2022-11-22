package com.switchfully.eurder.domain.order;

import java.time.LocalDate;

public class ItemGroup {
    private long itemId;
    private int amount;
    private LocalDate shippingDate;

    public ItemGroup(long itemId, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
