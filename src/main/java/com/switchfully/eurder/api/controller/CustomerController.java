package com.switchfully.eurder.api.controller;

import com.switchfully.eurder.api.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("customers")
@RestController

public class CustomerController {
    private final CustomerService customerService;

    private final Logger logger= LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCustomer(@RequestBody CreateCustomerDTO newCustomer){
        logger.info("New customer created");
        customerService.registerNewCustomer(newCustomer);
    }
}
