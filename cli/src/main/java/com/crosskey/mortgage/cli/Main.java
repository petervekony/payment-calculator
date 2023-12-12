package com.crosskey.mortgage.cli;

import java.io.FileReader;
import java.util.List;

import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.service.CoreCalculatorService;

public class Main {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("No file path provided");
      return;
    }

    String filePath = args[0];
    CoreCalculatorService service = new CoreCalculatorService();

    try (FileReader fileReader = new FileReader(filePath)) {
      List<CustomerLoanInfo> customerLoanInfos = service.parseFile(fileReader);

      for (CustomerLoanInfo info : customerLoanInfos) {
        System.out.println(info);
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
