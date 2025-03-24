package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "language_skills")
public class LanguageSkill {
  @Id
  private Long id;

  @Column(value = "language")
  private String language;

  @Column(value = "fluency_level")
  private String fluencyLevel;  // e.g., Beginner, Intermediate, Fluent, Native

  @Column(value = "certification")
  private String certification; // e.g., TOEFL, IELTS

  // Getters and Setters
  @Override
  public String toString() {
    return "LanguageSkill{id=" + id + ", language='" + language + "', fluencyLevel='" + fluencyLevel + "', certification='" + certification + "'}";
  }
}
