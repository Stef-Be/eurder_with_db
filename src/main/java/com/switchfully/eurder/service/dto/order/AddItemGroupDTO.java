package com.switchfully.eurder.service.dto.order;


import com.switchfully.eurder.domain.repository.OrderRepository;
import net.minidev.json.annotate.JsonIgnore;

public class AddItemGroupDTO {

    private OrderRepository orderRepository;
    private long itemId;
    private int amount;

    public AddItemGroupDTO(OrderRepository orderRepository, long itemId, int amount) {
        this.orderRepository = orderRepository;
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
