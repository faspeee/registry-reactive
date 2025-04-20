package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "health_records")
public class HealthRecord {
    @Id
    private UUID id;

    @Column(name = "health_condition")
    private String healthCondition;

    @Column(name = "medical_history")
    private String medicalHistory;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "policy_number")
    private String policyNumber;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "HealthRecord{id=" + id + ", healthCondition='" + healthCondition + "', medicalHistory='" + medicalHistory + "', insuranceProvider='" + insuranceProvider + "', policyNumber='" + policyNumber + "'}";
    }
}
