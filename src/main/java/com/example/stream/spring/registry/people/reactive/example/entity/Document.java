package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "documents")
public class Document {
    @Id
    private UUID id;

    @Column(name = "document_type")
    private String documentType; // e.g., ID card, Passport, Diploma

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "issue_date")
    private String issueDate;

    @Column(name = "expiry_date")
    private String expiryDate;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Document{id=" + id + ", documentType='" + documentType + "', documentNumber='" + documentNumber + "', issueDate='" + issueDate + "', expiryDate='" + expiryDate + "'}";
    }
}
