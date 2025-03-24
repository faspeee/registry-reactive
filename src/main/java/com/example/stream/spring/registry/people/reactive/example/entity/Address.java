package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "addresses")
public class Address {
  @Id
  private Long id;

  @Column(value = "street_address")
  private String streetAddress;

  @Column(value = "city")
  private String city;

  @Column(value = "postal_code")
  private String postalCode;

  @Column(value = "country")
  private String country;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Address{id=" + id + ", streetAddress='" + streetAddress + "', city='" + city + "', postalCode='" + postalCode + "', country='" + country + "'}";
  }
}
