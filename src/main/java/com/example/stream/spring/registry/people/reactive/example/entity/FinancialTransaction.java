package com.example.stream.spring.registry.people.reactive.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "financial_transactions")
public class FinancialTransaction {
  @Id
  private Long id;

  @Column(value = "transaction_type")
  private String transactionType;  // e.g., Deposit, Withdrawal, Investment

  @Column(value = "transaction_date")
  private String transactionDate;

  @Column(value = "amount")
  private Double amount;

  // Getters and Setters
  @Override
  public String toString() {
    return "FinancialTransaction{id=" + id + ", transactionType='" + transactionType + "', transactionDate='" + transactionDate + "', amount=" + amount + "}";
  }
}
