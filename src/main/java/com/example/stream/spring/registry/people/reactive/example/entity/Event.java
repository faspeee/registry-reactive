package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "events")
public class Event {
  @Id
  private Long id;

  @Column(value = "event_type")
  private String eventType; // e.g., Migration, Property Purchase

  @Column(value = "event_date")
  private String eventDate;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Event{id=" + id + ", eventType='" + eventType + "', eventDate='" + eventDate + "'}";
  }
}
