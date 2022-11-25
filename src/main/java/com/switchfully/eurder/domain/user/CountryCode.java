package com.switchfully.eurder.domain.user;

public enum CountryCode {
    BELGIUM("+32"),
    FRANCE("+33"),
    NETHERLANDS("+31");

    private String codeOfCountry;

    CountryCode(String countryCode) {
        this.codeOfCountry = countryCode;
    }

    public String getCountryCode() {
        return codeOfCountry;
    }
}
