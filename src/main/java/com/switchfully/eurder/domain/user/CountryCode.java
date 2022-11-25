package com.switchfully.eurder.domain.user;

public enum CountryCode {
    BELGIUM("+32"),
    FRANCE("+33"),
    NETHERLANDS("+31");

    private String countryCode;

    CountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
