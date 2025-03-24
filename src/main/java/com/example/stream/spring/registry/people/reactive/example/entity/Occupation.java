package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "occupations")
public class Occupation {
  @Id
  private Long id;

  @Column(value = "job_title")
  private String jobTitle;

  @Column(value = "employer")
  private String employer;

  @Column(value = "start_date")
  private String startDate;

  @Column(value = "end_date")
  private String endDate;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Occupation{id=" + id + ", jobTitle='" + jobTitle + "', employer='" + employer + "', startDate='" + startDate + "', endDate='" + endDate + "'}";
  }
}
