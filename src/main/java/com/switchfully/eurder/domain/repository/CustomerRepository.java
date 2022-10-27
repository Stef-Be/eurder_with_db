package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerRepository {
    private Map<String, Customer> customers;

    public CustomerRepository() {
        this.customers = new HashMap<>();
    }

    public void addNewCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public Customer getExactCustomer(String id) {
        return customers.get(id);
    }
}
