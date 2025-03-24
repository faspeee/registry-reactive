package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "insurances")
public class Insurance {
  @Id
  private Long id;

  @Column(value = "insurance_type")
  private String insuranceType;  // e.g., Health, Life, Vehicle

  @Column(value = "policy_number")
  private String policyNumber;

  @Column(value = "coverage_amount")
  private Double coverageAmount;

  // Getters and Setters
  @Override
  public String toString() {
    return "Insurance{id=" + id + ", insuranceType='" + insuranceType + "', policyNumber='" + policyNumber + "', coverageAmount=" + coverageAmount + "}";
  }
}
