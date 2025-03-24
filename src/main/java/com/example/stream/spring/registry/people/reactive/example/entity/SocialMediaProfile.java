package com.example.stream.spring.registry.people.reactive.example.entity;
// New Entities

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "social_media_profiles")
public class SocialMediaProfile {
  @Id
  private Long id;

  @Column(value = "platform")
  private String platform;  // e.g., Facebook, LinkedIn, Twitter

  @Column(value = "profile_url")
  private String profileUrl;

  // Getters and Setters
  @Override
  public String toString() {
    return "SocialMediaProfile{id=" + id + ", platform='" + platform + "', profileUrl='" + profileUrl + "'}";
  }
}
