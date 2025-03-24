package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "civil_registry")
public class CivilRegistry {
  @Id
  private Long id;

  @Column(value = "registry_name")
  private String registryName;
 
  private List<Integer> persons;


  @Override
  public String toString() {
    return "CivilRegistry{id=" + id + ", registryName='" + registryName + "', persons=" + persons + '}';
  }
}

