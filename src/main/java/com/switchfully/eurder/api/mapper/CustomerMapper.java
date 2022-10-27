package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.dto.CustomerDTO;
import com.switchfully.eurder.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer mapToCustomer(CreateCustomerDTO newCustomerDTO) {
        return new Customer(newCustomerDTO.getFirstName(), newCustomerDTO.getLastName(), newCustomerDTO.getEmail(), newCustomerDTO.getAddress(), newCustomerDTO.getPhoneNumber());
    }

    public CustomerDTO mapToDTO(Customer customer) {
        return new CustomerDTO()
                .setID(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setAddress(customer.getAddress())
                .setPhoneNumber(customer.getPhoneNumber());
    }
}
