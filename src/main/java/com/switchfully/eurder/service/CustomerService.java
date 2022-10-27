package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.api.dto.CustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        validationService.checkIfUserIsAlreadyCustomer(newCustomerDTO);
        customerRepository.addNewCustomer(customerMapper.mapToCustomer(newCustomerDTO));
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.getAllCustomers()
                .stream()
                .map(customer -> customerMapper.mapToDTO(customer))
                .collect(Collectors.toList());
    }
}
