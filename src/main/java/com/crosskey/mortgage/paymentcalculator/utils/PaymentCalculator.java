package com.crosskey.mortgage.paymentcalculator.utils;

public class PaymentCalculator {
  private PaymentCalculator() {}

  private static double toPower(double base, int exponent) {
    double result = 1;
    for (int i = 0; i < exponent; i++) {
      result *= base;
    }
    return result;
  }

  private static double round(double value) {
    double num = value * 100;

    long tmp = (long) (num + 0.5);

    return (double) tmp / 100;
  }

  public static double calculateMonthlyPayment(
      double totalLoan, double annualInterestRate, int numberOfYears) {
    double monthlyInterestRate = annualInterestRate / 100 / 12;
    int numberOfPayments = numberOfYears * 12;

    double factor = toPower(1 + monthlyInterestRate, numberOfPayments);

    return round(totalLoan * monthlyInterestRate * factor / (factor - 1));
  }
}
