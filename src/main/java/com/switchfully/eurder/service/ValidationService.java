package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
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
}
