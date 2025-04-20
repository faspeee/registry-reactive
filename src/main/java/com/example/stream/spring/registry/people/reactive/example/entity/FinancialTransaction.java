package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "financial_transactions")
public class FinancialTransaction {
    @Id
    private UUID id;

    @Column(name = "transaction_type")
    private String transactionType;  // e.g., Deposit, Withdrawal, Investment

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "amount")
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    // Getters and Setters
    @Override
    public String toString() {
        return "FinancialTransaction{id=" + id + ", transactionType='" + transactionType + "', transactionDate='" + transactionDate + "', amount=" + amount + "}";
    }
}
