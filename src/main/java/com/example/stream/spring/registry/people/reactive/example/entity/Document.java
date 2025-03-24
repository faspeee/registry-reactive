package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "documents")
public class Document {
  @Id
  private Long id;

  @Column(value = "document_type")
  private String documentType; // e.g., ID card, Passport, Diploma

  @Column(value = "document_number")
  private String documentNumber;

  @Column(value = "issue_date")
  private String issueDate;

  @Column(value = "expiry_date")
  private String expiryDate;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Document{id=" + id + ", documentType='" + documentType + "', documentNumber='" + documentNumber + "', issueDate='" + issueDate + "', expiryDate='" + expiryDate + "'}";
  }
}
