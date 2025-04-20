package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    private UUID id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Getters and Setters
    @Override
    public String toString() {
        return "BankAccount{id=" + id + ", accountNumber='" + accountNumber + "', bankName='" + bankName + "', balance=" + balance + "}";
    }
}
