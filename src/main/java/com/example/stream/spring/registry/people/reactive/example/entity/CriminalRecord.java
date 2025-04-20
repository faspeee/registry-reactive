package com.example.stream.spring.registry.people.reactive.example.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "criminal_records")
public class CriminalRecord {
    @Id
    private UUID id;

    @Column(name = "crime_type")
    private String crimeType;

    @Column(name = "crime_date")
    private String crimeDate;

    @Column(name = "punishment")
    private String punishment;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Getters and Setters
    @Override
    public String toString() {
        return "CriminalRecord{id=" + id + ", crimeType='" + crimeType + "', crimeDate='" + crimeDate + "', punishment='" + punishment + "'}";
    }
}

