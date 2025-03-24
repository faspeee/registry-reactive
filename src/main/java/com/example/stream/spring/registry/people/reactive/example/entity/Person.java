package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "persons")
public class Person {
  @Id
  private Long id;

  @Column(value = "first_name")
  private String firstName;

  @Column(value = "last_name")
  private String lastName;

  @Column(value = "date_of_birth")
  private String dateOfBirth;

  @Column(value = "gender")
  private String gender;

  private List<CivilRecord> civilRecords;

  private List<Address> addresses;

  private List<FamilyRelation> familyRelations;


  private List<Event> events;


  private List<Occupation> occupations;

  private List<Education> educationHistory;

  private List<Document> documents;

  private List<HealthRecord> healthRecords;

  private List<Property> properties;

  private List<SocialMediaProfile> socialMediaProfiles;

  private List<LanguageSkill> languageSkills;

  private List<CriminalRecord> criminalRecords;

  private List<BankAccount> bankAccounts;

  private List<Vehicle> vehicles;
 
  private List<FinancialTransaction> financialTransactions;

  private List<Insurance> insurances;

  // Getters and Setters
  // (Include getters and setters for each new field)

  @Override
  public String toString() {
    return "Person{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName + "', dateOfBirth='" + dateOfBirth + "', gender='" + gender + "', civilRecords=" + civilRecords + ", addresses=" + addresses + ", familyRelations=" + familyRelations + ", events=" + events + ", occupations=" + occupations + ", educationHistory=" + educationHistory + ", documents=" + documents + ", healthRecords=" + healthRecords + ", properties=" + properties + ", socialMediaProfiles=" + socialMediaProfiles + ", languageSkills=" + languageSkills + ", criminalRecords=" + criminalRecords + ", bankAccounts=" + bankAccounts + ", vehicles=" + vehicles + ", financialTransactions=" + financialTransactions + ", insurances=" + insurances + "}";
  }
}
