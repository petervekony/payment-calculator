package com.crosskey.mortgage.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.crosskey.mortgage.core.model.CustomerLoanInfo;

class PaymentCalculatorUtilsTest {

  @Test
  void testCalculateMonthlyPayment() {
    double totalLoan = 10000;
    double interestRate = 3.9;
    int years = 6;

    double totalLoan2 = 100000;
    double interestRate2 = 4.2;
    int years2 = 10;

    double expectedMonthlyPayment = 156.00;
    double expectedMonthlyPayment2 = 1021.98;

    double actualMonthlyPayment =
        PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan, interestRate, years);
    double actualMonthlyPayment2 =
        PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan2, interestRate2, years2);

    assertEquals(expectedMonthlyPayment, actualMonthlyPayment, 0.01);
    assertEquals(expectedMonthlyPayment2, actualMonthlyPayment2, 0.01);
  }

  @Test
  void testCalculateMonthlyPaymentZeroInterest() {
    double totalLoan = 6000;
    double interestRate = 0.0;
    int years = 5;

    double expectedMonthlyPayment = 100.00;
    double actualMonthlyPayment =
        PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan, interestRate, years);

    assertEquals(expectedMonthlyPayment, actualMonthlyPayment, 0.01);
  }

  @Test
  void testCalculateMonthlyPaymentNegativeLoan() {
    double totalLoan = -1000;
    double interestRate = 1.2;
    int years = 2;

    assertThrows(
        IllegalArgumentException.class,
        () -> PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan, interestRate, years));
  }

  @Test
  void testCalculateMonthlyPaymentNegativeInterest() {
    double totalLoan = 1000;
    double interestRate = -1.2;
    int years = 2;

    assertThrows(
        IllegalArgumentException.class,
        () -> PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan, interestRate, years));
  }

  @Test
  void testCalculateMonthlyPaymentNegativeYears() {
    double totalLoan = 1000;
    double interestRate = 1.2;
    int years = -2;

    assertThrows(
        IllegalArgumentException.class,
        () -> PaymentCalculatorUtils.calculateMonthlyPayment(totalLoan, interestRate, years));
  }

  @Test
  void testCalculateTotalCumulativeInterest() {
    double totalLoan = 10000;
    double monthlyPayment = 156.00;
    int years = 6;

    double expectedTotalInterest = 1232.00;
    double actualTotalInterest =
        PaymentCalculatorUtils.calculateTotalCumulativeInterest(totalLoan, monthlyPayment, years);

    assertEquals(expectedTotalInterest, actualTotalInterest, 0.01);
  }

  @Test
  void testCalculateMonthlyPaymentAndTotalCumulativeInterest() {
    List<CustomerLoanInfo> infoList = new ArrayList<>();
    infoList.add(new CustomerLoanInfo("Test Customer 1", 10000, 3.9, 6));
    infoList.add(new CustomerLoanInfo("Test Customer 2", 100000, 4.2, 10));

    infoList = PaymentCalculatorUtils.calculateMonthlyPaymentAndTotalCumulativeInterest(infoList);

    assertEquals(156.00, infoList.get(0).getMonthlyPayment(), 0.01);
    assertEquals(1232.00, infoList.get(0).getTotalCumulativeInterest(), 0.01);
    assertEquals(1021.98, infoList.get(1).getMonthlyPayment(), 0.01);
    assertEquals(22637.60, infoList.get(1).getTotalCumulativeInterest(), 0.01);
  }
}
