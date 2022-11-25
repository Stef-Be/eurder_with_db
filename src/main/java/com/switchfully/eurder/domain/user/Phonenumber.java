package com.switchfully.eurder.domain.user;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Phonenumber {
    private String phoneBody;

    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    public Phonenumber(){}

    public Phonenumber(String phoneBody, CountryCode countryCode) {
        this.phoneBody = phoneBody;
        this.countryCode = countryCode;
    }

    public String getPhoneBody() {
        return phoneBody;
    }

    public void setPhoneBody(String body) {
        this.phoneBody = body;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}