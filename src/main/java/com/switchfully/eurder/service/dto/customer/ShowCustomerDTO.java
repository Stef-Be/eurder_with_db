package com.switchfully.eurder.service.dto.customer;

import com.switchfully.eurder.domain.user.Phonenumber;
import com.switchfully.eurder.domain.user.address.Address;

public class ShowCustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private Phonenumber phoneNumber;

    public ShowCustomerDTO setID(long id) {
        this.id = id;
        return this;
    }

    public ShowCustomerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ShowCustomerDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ShowCustomerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public ShowCustomerDTO setAddress(Address address) {
        this.address = address;
        return this;
    }

    public ShowCustomerDTO setPhoneNumber(Phonenumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Phonenumber getPhoneNumber() {
        return phoneNumber;
    }
}
