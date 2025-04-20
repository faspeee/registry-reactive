package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Table(name = "persons")
public class Person {
    @Id
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender")
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
