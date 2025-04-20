package com.example.stream.spring.registry.people.reactive.example.entity;
// New Entities


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "social_media_profiles")
public class SocialMediaProfile {
    @Id
    private UUID id;

    @Column(name = "platform")
    private String platform;  // e.g., Facebook, LinkedIn, Twitter

    @Column(name = "profile_url")
    private String profileUrl;

    // Getters and Setters
    @Override
    public String toString() {
        return "SocialMediaProfile{id=" + id + ", platform='" + platform + "', profileUrl='" + profileUrl + "'}";
    }
}
