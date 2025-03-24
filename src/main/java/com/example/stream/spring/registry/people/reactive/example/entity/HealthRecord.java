package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "health_records")
public class HealthRecord {
  @Id
  private Long id;

  @Column(value = "health_condition")
  private String healthCondition;

  @Column(value = "medical_history")
  private String medicalHistory;

  @Column(value = "insurance_provider")
  private String insuranceProvider;

  @Column(value = "policy_number")
  private String policyNumber;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "HealthRecord{id=" + id + ", healthCondition='" + healthCondition + "', medicalHistory='" + medicalHistory + "', insuranceProvider='" + insuranceProvider + "', policyNumber='" + policyNumber + "'}";
  }
}
