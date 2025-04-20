package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "insurances")
public class Insurance {
    @Id
    private UUID id;

    @Column(name = "insurance_type")
    private String insuranceType;  // e.g., Health, Life, Vehicle

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "coverage_amount")
    private Double coverageAmount;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Getters and Setters
    @Override
    public String toString() {
        return "Insurance{id=" + id + ", insuranceType='" + insuranceType + "', policyNumber='" + policyNumber + "', coverageAmount=" + coverageAmount + "}";
    }
}
