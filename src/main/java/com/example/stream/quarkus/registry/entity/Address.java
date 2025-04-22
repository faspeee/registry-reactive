package com.example.stream.quarkus.registry.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;
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
