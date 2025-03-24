package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "family_relations")
public class FamilyRelation {
  @Id
  private Long id;

  @Column(value = "relation_type")
  private String relationType; // e.g., Parent, Spouse, Sibling

  private Person relatedPerson; // Related person (e.g., parent, spouse)

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "FamilyRelation{id=" + id + ", relationType='" + relationType + "', relatedPerson=" + relatedPerson + "}";
  }
}
