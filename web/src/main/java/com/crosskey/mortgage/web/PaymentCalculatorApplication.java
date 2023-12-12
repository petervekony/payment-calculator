package com.crosskey.mortgage.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.crosskey.mortgage.web", "com.crosskey.mortgage.core"})
public class PaymentCalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentCalculatorApplication.class, args);
  }
}
