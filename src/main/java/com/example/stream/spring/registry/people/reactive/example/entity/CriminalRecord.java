package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "criminal_records")
public class CriminalRecord {
  @Id
  private Long id;

  @Column(value = "crime_type")
  private String crimeType;

  @Column(value = "crime_date")
  private String crimeDate;

  @Column(value = "punishment")
  private String punishment;

  // Getters and Setters
  @Override
  public String toString() {
    return "CriminalRecord{id=" + id + ", crimeType='" + crimeType + "', crimeDate='" + crimeDate + "', punishment='" + punishment + "'}";
  }
}

