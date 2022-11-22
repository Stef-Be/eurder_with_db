package com.switchfully.eurder.domain.user;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Phonenumber {
    private String phone_body;

    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    public Phonenumber(){};

    public Phonenumber(String phone_body, CountryCode countryCode) {
        this.phone_body = phone_body;
        this.countryCode = countryCode;
    }

    public String getPhone_body() {
        return phone_body;
    }

    public void setPhone_body(String body) {
        this.phone_body = body;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}