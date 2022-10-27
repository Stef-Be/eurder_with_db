package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    public ValidationService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public void validateNoEmptyFieldsNewCustomer(CreateCustomerDTO newCustomerDTO) {
        validateFieldsNotNull(newCustomerDTO);
    }

    private void validateFieldsNotNull(CreateCustomerDTO newCustomerDTO) {
        validateFieldNotNull(newCustomerDTO.getFirstName(), "First name");
        validateFieldNotNull(newCustomerDTO.getLastName(), "Last name");
        validateFieldNotNull(newCustomerDTO.getEmail(), "Email");
        validateFieldNotNull(newCustomerDTO.getAddress(), "Address");
        validateFieldNotNull(newCustomerDTO.getPhoneNumber(), "Phone number");
    }

    private void validateFieldNotNull(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " can not be empty!");
    }

    public void checkIfUserIsAlreadyCustomer(CreateCustomerDTO newCustomerDTO) {
        Customer customerToAdd = customerMapper.mapToCreatedCustomer(newCustomerDTO);
        if (customerRepository.getAllCustomers().contains(customerToAdd)) {
            throw new IllegalArgumentException("This customer already exists!");
        }
    }
}
