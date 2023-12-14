package com.crosskey.mortgage.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.service.CoreCalculatorService;

public class MainTests {
  private static final String EXCEPTION_MSG = "Test exception";

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private PrintStream originalOut;
  private PrintStream originalErr;

  @BeforeEach
  public void setUpStreams() {
    originalOut = System.out;
    originalErr = System.err;
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  void testMainMethodWithValidInput() throws IOException {
    CoreCalculatorService mockService = mock(CoreCalculatorService.class);
    List<CustomerLoanInfo> mockInfoList = new ArrayList<>();
    CustomerLoanInfo alice = new CustomerLoanInfo("Alice", 1000, 4, 3, 29.52, 62.86);
    CustomerLoanInfo bob = new CustomerLoanInfo("Bob", 3000, 5.2, 4, 69.63, 329.28);
    mockInfoList.add(alice);
    mockInfoList.add(bob);
    when(mockService.parseFile(any())).thenReturn(mockInfoList);
    when(mockService.calculateMonthlyPaymentsAndTotalCumulativeInterest(mockInfoList))
        .thenReturn(mockInfoList);

    String fileContent =
        "Customer,Total loan,Interest,Years\n" + "Alice,1000,4,3\n" + "Bob,3000,5.2,4\n";
    File tempFile = File.createTempFile("test", ".csv");
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    writer.write(fileContent);
    writer.close();
    Main main = new Main(mockService);
    main.run(new String[] {tempFile.getAbsolutePath()});

    String expectedOutput =
        "****************************************************************************************************\n"
            + "Prospect 1: Alice wants to borrow 1000.00 € for a period of 3 years and pay 29.52 €"
            + " each month\n"
            + "Prospect 2: Bob wants to borrow 3000.00 € for a period of 4 years and pay 69.63 €"
            + " each month\n"
            + "****************************************************************************************************";

    assertEquals(expectedOutput, outContent.toString().trim());

    tempFile.deleteOnExit();
  }

  @Test
  void testMainMethodFileNotFound() throws IOException {
    CoreCalculatorService mockService = mock(CoreCalculatorService.class);
    when(mockService.parseFile(any())).thenThrow(new IOException(EXCEPTION_MSG));

    Main main = new Main(mockService);
    main.run(new String[] {"mockFilePath"});

    restoreStreams();
    assertTrue(errContent.toString().contains("No such file or directory"));
  }

  @Test
  void testMainMethodExceptionHandling() throws IOException {
    File tempFile = File.createTempFile("test", ".csv");
    tempFile.deleteOnExit();

    CoreCalculatorService mockService = mock(CoreCalculatorService.class);
    when(mockService.parseFile(any())).thenThrow(new IOException(EXCEPTION_MSG));

    Main main = new Main(mockService);
    main.run(new String[] {tempFile.getAbsolutePath()});

    restoreStreams();
    assertTrue(errContent.toString().contains(EXCEPTION_MSG));
  }
}
