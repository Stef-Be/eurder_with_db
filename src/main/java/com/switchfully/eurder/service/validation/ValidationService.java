package com.switchfully.eurder.service.validation;


import com.switchfully.eurder.domain.user.Phonenumber;
import com.switchfully.eurder.domain.user.address.Address;
import org.springframework.stereotype.Service;
@Service
public abstract class ValidationService {
    protected void validateFieldNotNull(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " can not be empty!");
    }

    protected void valdiateAddressNotNull(Address address){
        if (address == null) throw new IllegalArgumentException("Address can not be empty!");
    }

    protected void validatePhoneNumberNotNull(Phonenumber phoneNumber) {
        if(phoneNumber == null) throw new IllegalArgumentException("Phonenumber can not be empty!");
    }

    protected void validateFieldNotZero(int amount) {
        if(amount <= 0) throw new IllegalArgumentException("Amount must be more than 0!");
    }

    protected void validateFieldNotZero(double price) {
        if(price <= 0) throw new IllegalArgumentException("Price must be more than 0!");
    }

    protected void validateIdNotZero(double id){
        if (id <= 0) throw new IllegalArgumentException("ID must be a positive number!");
    }
}
