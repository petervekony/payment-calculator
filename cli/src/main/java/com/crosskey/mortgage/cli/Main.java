package com.crosskey.mortgage.cli;

import java.io.FileReader;
import java.util.List;
import java.util.Locale;

import com.crosskey.mortgage.cli.exception.ExceptionHandler;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.service.CoreCalculatorService;

public class Main {
  private final CoreCalculatorService service;

  public Main(CoreCalculatorService service) {
    this.service = service;
  }

  public void run(String[] args) {
    try {
      if (args.length == 0) {
        System.out.println("Error: No file path provided");
        return;
      }

      String filePath = args[0];
      try (FileReader fileReader = new FileReader(filePath)) {
        List<CustomerLoanInfo> customerLoanInfos = service.parseFile(fileReader);
        customerLoanInfos =
            service.calculateMonthlyPaymentsAndTotalCumulativeInterest(customerLoanInfos);

        if (!customerLoanInfos.isEmpty()) {
          int counter = 1;
          String padding =
              "****************************************************************************************************";
          System.out.println(padding);
          for (CustomerLoanInfo info : customerLoanInfos) {
            String output =
                String.format(
                    Locale.ENGLISH,
                    "Prospect %d: %s wants to borrow %.2f € for a period of %d years and pay %.2f €"
                        + " each month",
                    counter,
                    info.getCustomer(),
                    info.getTotalLoan(),
                    info.getYears(),
                    info.getMonthlyPayment());
            System.out.println(output);
            counter++;
          }
          System.out.println(padding);
        }
      }
    } catch (Exception e) {
      ExceptionHandler.handleException(e);
    }
  }

  public static void main(String[] args) {
    CoreCalculatorService service = new CoreCalculatorService();
    Main app = new Main(service);
    app.run(args);
  }
}
