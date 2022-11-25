package com.switchfully.eurder.domain.user.address;

import javax.persistence.Embeddable;
@Embeddable
public class PostalCode {
    private String code;
    private String city;

    public PostalCode(){
    };

    public PostalCode(String code, String city) {
        this.code = code;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String postalCode) {
        this.code = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}