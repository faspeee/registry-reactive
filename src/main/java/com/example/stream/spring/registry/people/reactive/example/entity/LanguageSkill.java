package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "language_skills")
public class LanguageSkill {
    @Id
    private UUID id;

    @Column(name = "language")
    private String language;

    @Column(name = "fluency_level")
    private String fluencyLevel;  // e.g., Beginner, Intermediate, Fluent, Native

    @Column(name = "certification")
    private String certification; // e.g., TOEFL, IELTS

    // Getters and Setters
    @Override
    public String toString() {
        return "LanguageSkill{id=" + id + ", language='" + language + "', fluencyLevel='" + fluencyLevel + "', certification='" + certification + "'}";
    }
}
