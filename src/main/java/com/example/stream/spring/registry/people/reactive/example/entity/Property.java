package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "properties")
public class Property {
  @Id
  private Long id;

  @Column(value = "property_type")
  private String propertyType; // e.g., House, Land, Vehicle

  @Column(value = "property_value")
  private String propertyValue;

  @Column(value = "address")
  private String address;

  @Column(value = "ownership_start_date")
  private String ownershipStartDate;

  @Column(value = "ownership_end_date")
  private String ownershipEndDate;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Property{id=" + id + ", propertyType='" + propertyType + "', propertyValue='" + propertyValue + "', address='" + address + "', ownershipStartDate='" + ownershipStartDate + "', ownershipEndDate='" + ownershipEndDate + "'}";
  }
}
