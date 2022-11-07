package com.switchfully.eurder.domain.order;

import java.util.List;
import java.util.UUID;

public class Order {
    private final List<ItemGroup> itemGroups;
    private final String customerId;

    public Order(List<ItemGroup> items) {
        this.customerId = UUID.randomUUID().toString();
        this.itemGroups = items;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public String getCustomerId() {
        return customerId;
    }
}
