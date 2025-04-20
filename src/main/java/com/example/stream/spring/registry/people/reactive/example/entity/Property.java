package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "properties")
public class Property {
    @Id
    private UUID id;

    @Column(name = "property_type")
    private String propertyType; // e.g., House, Land, Vehicle

    @Column(name = "property_value")
    private String propertyValue;

    @Column(name = "address")
    private String address;

    @Column(name = "ownership_start_date")
    private String ownershipStartDate;

    @Column(name = "ownership_end_date")
    private String ownershipEndDate;

    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Property{id=" + id + ", propertyType='" + propertyType + "', propertyValue='" + propertyValue + "', address='" + address + "', ownershipStartDate='" + ownershipStartDate + "', ownershipEndDate='" + ownershipEndDate + "'}";
    }
}
