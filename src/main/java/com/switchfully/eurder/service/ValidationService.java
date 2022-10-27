package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.AddItemDTO;
import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.user.Customer;
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

    public void validateNoEmptyFields(CreateCustomerDTO newCustomerDTO) {
        validateFieldsNotNull(newCustomerDTO);
    }

    public void validateNoEmptyFields(AddItemDTO newItem) {
        validateFieldsNotNull(newItem);
    }

    private void validateFieldsNotNull(CreateCustomerDTO newCustomerDTO) {
        validateFieldNotNull(newCustomerDTO.getFirstName(), "First name");
        validateFieldNotNull(newCustomerDTO.getLastName(), "Last name");
        validateFieldNotNull(newCustomerDTO.getEmail(), "Email");
        validateFieldNotNull(newCustomerDTO.getAddress(), "Address");
        validateFieldNotNull(newCustomerDTO.getPhoneNumber(), "Phone number");
    }

    private void validateFieldsNotNull(AddItemDTO newItem) {
        validateFieldNotNull(newItem.getName(), "Name");
        validateFieldNotNull(newItem.getDescription(), "Description");
        validateFieldNotZero(newItem.getPrice());
        validateFieldNotZero(newItem.getAmount());
    }

    private void validateFieldNotZero(double price) {
        if(price == 0) throw new IllegalArgumentException("Price can not be zero!");
    }
    private void validateFieldNotZero(int amount) {
        if(amount == 0) throw new IllegalArgumentException("Amount can not be zero!");
    }

    private void validateFieldNotNull(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " can not be empty!");
    }

    public void checkIfUserIsAlreadyCustomer(CreateCustomerDTO newCustomerDTO) {
        Customer customerToAdd = customerMapper.mapToCreatedCustomer(newCustomerDTO);
        if (customerRepository.getAllCustomers().contains(customerToAdd)) {
            throw new IllegalArgumentException("You are already a customer!");
        }
    }

}
