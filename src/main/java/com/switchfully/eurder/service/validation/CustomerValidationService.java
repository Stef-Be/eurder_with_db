package com.switchfully.eurder.service.validation;

import com.switchfully.eurder.api.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerValidationService extends ValidationService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerValidationService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }
    public void validateNoEmptyFields(CreateCustomerDTO newCustomerDTO) {
        validateFieldsNotNull(newCustomerDTO);
    }

    private void validateFieldsNotNull(CreateCustomerDTO newCustomerDTO) {
        validateFieldNotNull(newCustomerDTO.getFirstName(), "First name");
        validateFieldNotNull(newCustomerDTO.getLastName(), "Last name");
        validateFieldNotNull(newCustomerDTO.getEmail(), "Email");
        validateFieldNotNull(newCustomerDTO.getAddress(), "Address");
        validateFieldNotNull(newCustomerDTO.getPhoneNumber(), "Phone number");
    }
    public void checkIfUserIsAlreadyCustomer(CreateCustomerDTO newCustomerDTO) {
        Customer customerToAdd = customerMapper.mapToCreatedCustomer(newCustomerDTO);
        if (customerRepository.getAllCustomers().contains(customerToAdd)) {
            throw new IllegalArgumentException("You are already a customer!");
        }
    }
}
