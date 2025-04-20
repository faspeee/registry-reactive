package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "addresses")
public class Address {
    @Id
    private UUID id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;
    
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Address{id=" + id + ", streetAddress='" + streetAddress + "', city='" + city + "', postalCode='" + postalCode + "', country='" + country + "'}";
    }
}
