package com.crosskey.mortgage.paymentcalculator.utils;

import java.util.List;

import com.crosskey.mortgage.paymentcalculator.model.CustomerLoanInfo;

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

  public static double calculateTotalCumulativeInterest(
      double totalLoan, double monthlyPayment, int numberOfYears) {
    int numberOfPayments = numberOfYears * 12;
    double totalAmountPaid = monthlyPayment * numberOfPayments;
    return round(totalAmountPaid - totalLoan);
  }

  public static List<CustomerLoanInfo> calculateMonthlyPaymentAndTotalCumulativeInterest(
      List<CustomerLoanInfo> infoList) {
    for (CustomerLoanInfo info : infoList) {
      info.setMonthlyPayment(
          calculateMonthlyPayment(info.getTotalLoan(), info.getInterest(), info.getYears()));
      info.setTotalCumulativeInterest(
          calculateTotalCumulativeInterest(
              info.getTotalLoan(), info.getMonthlyPayment(), info.getYears()));
    }
    return infoList;
  }
}
