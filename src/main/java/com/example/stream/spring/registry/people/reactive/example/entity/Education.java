package com.example.stream.spring.registry.people.reactive.example.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "education")
public class Education {
    @Id
    private UUID id;

    @Column(name = "degree")
    private String degree;

    @Column(name = "university")
    private String university;

    @Column(name = "graduation_year")
    private String graduationYear;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Education{id=" + id + ", degree='" + degree + "', university='" + university + "', graduationYear='" + graduationYear + "'}";
    }
}
