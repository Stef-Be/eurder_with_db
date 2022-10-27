package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final CustomerRepository customerRepository;

    public CustomerMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer mapToCustomer(CreateCustomerDTO newCustomerDTO) {
        return new Customer(newCustomerDTO.getFirstName(), newCustomerDTO.getLastName(), newCustomerDTO.getEmail(), newCustomerDTO.getAddress(), newCustomerDTO.getPhoneNumber());
    }
}
