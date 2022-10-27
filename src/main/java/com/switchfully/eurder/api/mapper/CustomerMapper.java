package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.dto.CustomerDTO;
import com.switchfully.eurder.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer mapToCreatedCustomer(CreateCustomerDTO newCustomerDTO) {
        return new Customer(newCustomerDTO.getFirstName(), newCustomerDTO.getLastName(), newCustomerDTO.getEmail(), newCustomerDTO.getAddress(), newCustomerDTO.getPhoneNumber());
    }

    public Customer mapToCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getEmail(), customerDTO.getAddress(), customerDTO.getPhoneNumber());
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
