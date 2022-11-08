package com.switchfully.eurder.service;

import com.switchfully.eurder.api.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.api.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.api.mapper.CustomerMapper;
import com.switchfully.eurder.domain.repository.CustomerRepository;
import com.switchfully.eurder.service.validation.CustomerValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.eurder.domain.user.Feature.VIEW_CUSTOMERS;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidationService customerValidationService;
    private final SecurityService securityService;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, CustomerValidationService customerValidationService, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.customerValidationService = customerValidationService;
        this.securityService = securityService;
    }

    public void registerNewCustomer(CreateCustomerDTO newCustomerDTO) {
        customerValidationService.validateNoEmptyFields(newCustomerDTO);
        customerValidationService.checkIfUserIsAlreadyCustomer(newCustomerDTO);
        customerRepository.addNewCustomer(customerMapper.mapToCreatedCustomer(newCustomerDTO));
    }

    public List<ShowCustomerDTO> getAllCustomers(String authorization) {
        securityService.validateAuthorization(authorization, VIEW_CUSTOMERS);
        return customerRepository.getAllCustomers()
                .stream()
                .map(customerMapper::mapToShowDTO)
                .collect(Collectors.toList());
    }

    public ShowCustomerDTO getExactCustomer(String id, String authorization) {
        securityService.validateAuthorization(authorization, VIEW_CUSTOMERS);
        return customerMapper.mapToShowDTO(customerRepository.getExactCustomer(id));
    }
}
