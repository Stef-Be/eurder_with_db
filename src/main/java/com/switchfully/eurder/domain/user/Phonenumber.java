package com.switchfully.eurder.domain.user;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Phonenumber {
    private String body;

    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    public Phonenumber(){};

    public Phonenumber(String body, CountryCode countryCode) {
        this.body = body;
        this.countryCode = countryCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}