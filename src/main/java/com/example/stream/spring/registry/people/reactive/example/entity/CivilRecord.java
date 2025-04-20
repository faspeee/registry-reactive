package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "civil_records")
public class CivilRecord {
    @Id
    private UUID id;

    @Column(name = "record_type")
    private String recordType;  // For example: Birth, Marriage, Death

    @Column(name = "record_date")
    private String recordDate;

    @Column(name = "registration_number")
    private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "CivilRecord{id=" + id + ", recordType='" + recordType + "', recordDate='" + recordDate + "', registrationNumber='" + registrationNumber + "'}";
    }
}

