package com.crosskey.mortgage.paymentcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanInfo {
  private String customer;
  private double totalLoan;
  private double interest;
  private int years;
  private double monthlyPayment;

  public CustomerLoanInfo(String customer, double totalLoan, double interest, int years) {
    this.customer = customer;
    this.totalLoan = totalLoan;
    this.interest = interest;
    this.years = years;
  }
}
