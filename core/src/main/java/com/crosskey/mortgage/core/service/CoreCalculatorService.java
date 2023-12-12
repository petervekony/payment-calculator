package com.crosskey.mortgage.core.service;

import com.crosskey.mortgage.core.exception.InvalidNumberFormatException;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.utils.PaymentCalculatorUtils;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoreCalculatorService {
  public List<CustomerLoanInfo> parseFile(Reader reader)
      throws IOException, InvalidNumberFormatException {
    String[] headers = new String[] {"Customer", "Total loan", "Interest", "Years"};
    CSVFormat csvFormat =
        CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).build();

    List<CustomerLoanInfo> customerList = new ArrayList<>();

    Iterable<CSVRecord> csvRecords = csvFormat.parse(reader);
    for (CSVRecord csvRecord : csvRecords) {
      if (csvRecord.size() != headers.length) {
        continue;
      }
      try {
        String customer = csvRecord.get("Customer");
        double totalLoan = Double.parseDouble(csvRecord.get("Total loan"));
        double interest = Double.parseDouble(csvRecord.get("Interest"));
        int years = Integer.parseInt(csvRecord.get("Years"));
        CustomerLoanInfo info = new CustomerLoanInfo(customer, totalLoan, interest, years);
        customerList.add(info);
      } catch (NumberFormatException e) {
        throw new InvalidNumberFormatException(
            "Invalid number format in the file: " + e.getMessage());
      }
    }

    return customerList;
  }

  public List<CustomerLoanInfo> calculateMonthlyPaymentsAndTotalCumulativeInterest(
      List<CustomerLoanInfo> customerList) {
    for (CustomerLoanInfo info : customerList) {
      info.setMonthlyPayment(
          PaymentCalculatorUtils.calculateMonthlyPayment(
              info.getTotalLoan(), info.getInterest(), info.getYears()));
    }
    return customerList;
  }
}
