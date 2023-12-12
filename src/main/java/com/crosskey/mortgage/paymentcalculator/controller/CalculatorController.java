package com.crosskey.mortgage.paymentcalculator.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.paymentcalculator.exception.FileEmptyException;
import com.crosskey.mortgage.paymentcalculator.exception.FileSizeLimitExceededException;
import com.crosskey.mortgage.paymentcalculator.exception.InvalidNumberFormatException;
import com.crosskey.mortgage.paymentcalculator.exception.UnsupportedFileTypeException;
import com.crosskey.mortgage.paymentcalculator.model.CustomerLoanInfo;
import com.crosskey.mortgage.paymentcalculator.service.CalculatorService;
import com.crosskey.mortgage.paymentcalculator.utils.PaymentCalculatorUtils;

@RestController
@RequestMapping("/api")
public class CalculatorController {

  private CalculatorService calculatorService;

  @Autowired
  public CalculatorController(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  @PostMapping("/calculate")
  public ResponseEntity<List<CustomerLoanInfo>> calculateMonthlyPayments(
      @RequestPart MultipartFile file)
      throws UnsupportedFileTypeException,
          FileSizeLimitExceededException,
          FileEmptyException,
          InvalidNumberFormatException,
          IOException {
    List<CustomerLoanInfo> info = calculatorService.parseFile(file);
    info = PaymentCalculatorUtils.calculateMonthlyPaymentAndTotalCumulativeInterest(info);

    return new ResponseEntity<>(info, HttpStatus.OK);
  }
}
