package com.switchfully.eurder.api.dto.customer;

public class ShowCustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;

    public ShowCustomerDTO setID(String id) {
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

    public ShowCustomerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public ShowCustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getId() {
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

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
