package com.switchfully.eurder.domain.order;

import java.util.List;
import java.util.UUID;

public class Order {
    private final List<ItemGroup> items;
    private final String customerId;

    public Order(List<ItemGroup> items) {
        this.customerId = UUID.randomUUID().toString();
        this.items = items;
    }

    public List<ItemGroup> getItems() {
        return items;
    }

    public String getCustomerId() {
        return customerId;
    }
}
