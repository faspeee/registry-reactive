package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "education")
public class Education {
  @Id
  private Long id;

  @Column(value = "degree")
  private String degree;

  @Column(value = "university")
  private String university;

  @Column(value = "graduation_year")
  private String graduationYear;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Education{id=" + id + ", degree='" + degree + "', university='" + university + "', graduationYear='" + graduationYear + "'}";
  }
}
