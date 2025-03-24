package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "vehicles")
public class Vehicle {
  @Id
  private Long id;

  @Column(value = "vehicle_type")
  private String vehicleType;  // e.g., Car, Motorcycle, Truck

  @Column(value = "registration_number")
  private String registrationNumber;

  @Column(value = "ownership_start_date")
  private String ownershipStartDate;

  // Getters and Setters
  @Override
  public String toString() {
    return "Vehicle{id=" + id + ", vehicleType='" + vehicleType + "', registrationNumber='" + registrationNumber + "', ownershipStartDate='" + ownershipStartDate + "'}";
  }
}
