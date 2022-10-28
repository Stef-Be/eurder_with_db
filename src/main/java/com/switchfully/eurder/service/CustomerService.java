package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.api.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.eurder.domain.user.Feature.VIEW_CUSTOMERS;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ValidationService validationService;
    private final SecurityService securityService;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, ValidationService validationService, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.validationService = validationService;
        this.securityService = securityService;
    }

    public void registerNewCustomer(CreateCustomerDTO newCustomerDTO) {
        validationService.validateNoEmptyFields(newCustomerDTO);
        validationService.checkIfUserIsAlreadyCustomer(newCustomerDTO);
        customerRepository.addNewCustomer(customerMapper.mapToCreatedCustomer(newCustomerDTO));
    }

    public List<ShowCustomerDTO> getAllCustomers(String authorization) {
        securityService.validateAuthorization(authorization, VIEW_CUSTOMERS);
        return customerRepository.getAllCustomers()
                .stream()
                .map(customer -> customerMapper.mapToShowDTO(customer))
                .collect(Collectors.toList());
    }

    public ShowCustomerDTO getExactCustomer(String id, String authorization) {
        securityService.validateAuthorization(authorization, VIEW_CUSTOMERS);
        return customerMapper.mapToShowDTO(customerRepository.getExactCustomer(id));
    }
}
