package com.switchfully.eurder.api.mapper;

import com.switchfully.eurder.service.SecurityService;
import com.switchfully.eurder.service.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private SecurityService securityService;

    public CustomerMapper(SecurityService securityService) {
        this.securityService = securityService;
    }

    public Customer mapToCreatedCustomer(CreateCustomerDTO newCustomerDTO) {
        return new Customer(newCustomerDTO.getFirstName(),
                            newCustomerDTO.getLastName(),
                            newCustomerDTO.getEmail(),
                            newCustomerDTO.getAddress(),
                            newCustomerDTO.getPhoneNumber(),
                            securityService.signup(newCustomerDTO.getPassword()));
    }

    public Customer mapToCustomerToShow(ShowCustomerDTO showCustomerDTO) {
        return new Customer(showCustomerDTO.getFirstName(),
                showCustomerDTO.getLastName(),
                showCustomerDTO.getEmail(),
                showCustomerDTO.getAddress(),
                showCustomerDTO.getPhoneNumber(),null);
    }

    public ShowCustomerDTO mapToShowDTO(Customer customer) {
        return new ShowCustomerDTO().setID(customer.getId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setAddress(customer.getAddress())
                .setPhoneNumber(customer.getPhoneNumber());
    }
}
