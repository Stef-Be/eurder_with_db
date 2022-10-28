package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderRepository {

    private Map<Customer, Order> orderRepository;
    public void addNewOrder(Customer orderingCustomer, Order orderToAdd) {
        orderRepository.put(orderingCustomer, orderToAdd);
    }
}
