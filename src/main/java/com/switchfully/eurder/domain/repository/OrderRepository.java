package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.api.dto.AddOrderDTO;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderRepository {

    private Map<Customer, Order> orderRepository;

    private ItemRepository itemRepository;

    public OrderRepository(ItemRepository itemRepository) {
        this.orderRepository = new HashMap<>();
        this.itemRepository = itemRepository;
    }

    public void addNewOrder(Customer orderingCustomer, Order orderToAdd) {
        orderRepository.put(orderingCustomer, orderToAdd);
    }

    public double calculateFinalPrice(AddOrderDTO newOrder) {
        return newOrder.getItemGroupDTOList().stream().mapToDouble(itemGroupDTO -> itemRepository.getItemPrice(itemGroupDTO.getItemId())).sum();
    }
}
