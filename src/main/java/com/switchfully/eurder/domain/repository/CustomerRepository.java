package com.switchfully.eurder.domain.repository;

import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Role;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerRepository {
    private Map<String, Customer> customers;

    public CustomerRepository() {
        this.customers = new HashMap<>();
        setupDataBase();
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

    public Customer getCustomerbyEmail(String email) {
        return customers.values().stream().filter(customer -> customer.getEmail().equals(email)).findFirst().orElseThrow();
    }

    private void setupDataBase(){
        Customer adminSteve = new Customer("Steve","The Chief","admin@eurder.com", "funstreet 100", "1207", "password");
        adminSteve.setRole(Role.ADMIN);
        addNewCustomer(adminSteve);
    }
}
