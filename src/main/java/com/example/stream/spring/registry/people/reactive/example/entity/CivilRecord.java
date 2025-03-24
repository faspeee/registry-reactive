package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "civil_records")
public class CivilRecord {
  @Id
  private Long id;

  @Column(value = "record_type")
  private String recordType;  // For example: Birth, Marriage, Death

  @Column(value = "record_date")
  private String recordDate;

  @Column(value = "registration_number")
  private String registrationNumber;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "CivilRecord{id=" + id + ", recordType='" + recordType + "', recordDate='" + recordDate + "', registrationNumber='" + registrationNumber + "'}";
  }
}

