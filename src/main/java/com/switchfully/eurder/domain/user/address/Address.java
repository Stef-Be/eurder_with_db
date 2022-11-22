package com.switchfully.eurder.domain.user.address;

import javax.persistence.*;

@Entity
@Table(name="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;
    @Column(name = "streetname")
    private String streetName;
    @Column(name = "housenumber")
    private String houseNumber;
    @Embedded
    private PostalCode postalCode;
    @Column(name = "country")
    private String country;

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public Address() {

    }

    public Address(String streetName, String houseNumber, PostalCode postalCode, String country) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.country = country;
    }
}
