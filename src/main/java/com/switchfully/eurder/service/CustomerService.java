package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final ValidationService validationService;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, ValidationService validationService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.validationService = validationService;
    }

    public void registerNewCustomer(CreateCustomerDTO newCustomerDTO) {
        validationService.validateNoEmptyFieldsNewCustomer(newCustomerDTO);
        customerRepository.addNewCustomer(customerMapper.mapToCustomer(newCustomerDTO));
    }
}
