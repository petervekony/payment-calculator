package com.crosskey.mortgage.paymentcalculator.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.paymentcalculator.model.CustomerLoanInfo;
import com.crosskey.mortgage.paymentcalculator.service.CalculatorService;

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
      @RequestParam("file") MultipartFile file) {
    try {
      List<CustomerLoanInfo> info = calculatorService.parseFile(file);
      info = calculatorService.calculateMonthlyPayments(info);
      return new ResponseEntity<>(info, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
