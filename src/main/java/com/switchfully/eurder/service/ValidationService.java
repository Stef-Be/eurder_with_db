package com.switchfully.eurder.service;


import com.switchfully.eurder.api.dto.order.AddItemGroupDTO;
import com.switchfully.eurder.api.dto.order.AddOrderDTO;

import org.springframework.stereotype.Service;
@Service
public class ValidationService {
    protected void validateFieldNotNull(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " can not be empty!");
    }

    protected void validateFieldNotZero(int amount) {
        if(amount <= 0) throw new IllegalArgumentException("Amount must be more than 0!");
    }

    protected void validateFieldNotZero(double price) {
        if(price <= 0) throw new IllegalArgumentException("Price must be more than 0!");
    }
}
