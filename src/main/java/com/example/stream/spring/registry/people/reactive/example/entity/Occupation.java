package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "occupations")
public class Occupation {
    @Id
    private UUID id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "employer")
    private String employer;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Occupation{id=" + id + ", jobTitle='" + jobTitle + "', employer='" + employer + "', startDate='" + startDate + "', endDate='" + endDate + "'}";
    }
}
