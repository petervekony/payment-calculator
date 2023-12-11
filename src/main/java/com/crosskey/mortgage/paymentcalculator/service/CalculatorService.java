package com.crosskey.mortgage.paymentcalculator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.paymentcalculator.model.CustomerLoanInfo;

@Service
public class CalculatorService {
  public List<CustomerLoanInfo> parseFile(MultipartFile file) throws IOException {
    BufferedReader fileReader =
        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
    String[] headers = new String[] {"Customer", "Total loan", "Interest", "Years"};
    CSVFormat csvFormat =
        CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).build();

    List<CustomerLoanInfo> customerList = new ArrayList<>();

    Iterable<CSVRecord> csvRecords = csvFormat.parse(fileReader);
    for (CSVRecord csvRecord : csvRecords) {
      String customer = csvRecord.get("Customer");
      double totalLoan = Double.parseDouble(csvRecord.get("Total loan"));
      double interest = Double.parseDouble(csvRecord.get("Interest"));
      int years = Integer.parseInt(csvRecord.get("Years"));
      CustomerLoanInfo info = new CustomerLoanInfo(customer, totalLoan, interest, years);
      customerList.add(info);
    }

    return customerList;
  }

  // public List<CustomerLoanInfo> calculateMonthlyPayments(List<CustomerLoanInfo> customerList) {}
}
