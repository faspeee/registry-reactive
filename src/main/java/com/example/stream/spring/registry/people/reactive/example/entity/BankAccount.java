package com.example.stream.spring.registry.people.reactive.example.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "bank_accounts")
public class BankAccount {
  @Id
  private Long id;

  @Column(value = "account_number")
  private String accountNumber;

  @Column(value = "bank_name")
  private String bankName;

  @Column(value = "balance")
  private Double balance;

  // Getters and Setters
  @Override
  public String toString() {
    return "BankAccount{id=" + id + ", accountNumber='" + accountNumber + "', bankName='" + bankName + "', balance=" + balance + "}";
  }
}
