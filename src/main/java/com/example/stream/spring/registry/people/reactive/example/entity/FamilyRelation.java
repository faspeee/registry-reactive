package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "family_relations")
public class FamilyRelation {
    @Id
    private UUID id;

    @Column(name = "relation_type")
    private String relationType; // e.g., Parent, Spouse, Sibling

    private Person relatedPerson; // Related person (e.g., parent, spouse)

    // Getters and Setters
    // (Include getters and setters for each new field)

    @Override
    public String toString() {
        return "FamilyRelation{id=" + id + ", relationType='" + relationType + "', relatedPerson=" + relatedPerson + "}";
    }
}
