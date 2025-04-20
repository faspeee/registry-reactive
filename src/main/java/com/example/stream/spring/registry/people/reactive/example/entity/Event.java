package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "events")
public class Event {
    @Id
    private UUID id;

    @Column(name = "event_type")
    private String eventType; // e.g., Migration, Property Purchase

    @Column(name = "event_date")
    private String eventDate;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "Event{id=" + id + ", eventType='" + eventType + "', eventDate='" + eventDate + "'}";
    }
}
