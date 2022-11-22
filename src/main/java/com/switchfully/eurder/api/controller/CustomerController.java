package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.service.dto.customer.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.customer.ShowCustomerDTO;
import com.switchfully.eurder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("customers")
@RestController

public class CustomerController {
    private final CustomerService customerService;

    private final Logger logger= LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCustomer(@RequestBody CreateCustomerDTO newCustomer){
        logger.info("Creating new customer");
        customerService.registerNewCustomer(newCustomer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShowCustomerDTO> getAllCustomers(@RequestHeader String authorization){
        logger.info("Getting all customers");
        return customerService.getAllCustomers(authorization);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ShowCustomerDTO getExactCustomer(@PathVariable Long id, @RequestHeader String authorization){
        logger.info("Getting a customer");
        return customerService.getExactCustomer(id, authorization);
    }
}
