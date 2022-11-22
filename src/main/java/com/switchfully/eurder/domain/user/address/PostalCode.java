package com.switchfully.eurder.domain.user.address;

import javax.persistence.Embeddable;


@Embeddable
public class PostalCode {
    private String postalCode;
    private String city;

    public PostalCode(){
    };

    public PostalCode(String postalCode, String city) {
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}