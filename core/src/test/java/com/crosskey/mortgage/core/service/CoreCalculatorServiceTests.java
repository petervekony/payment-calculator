package com.crosskey.mortgage.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crosskey.mortgage.core.exception.InvalidNumberFormatException;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;

public class CoreCalculatorServiceTests {
  private CoreCalculatorService service;

  @BeforeEach
  void setUp() {
    service = new CoreCalculatorService();
  }

  @Test
  void testParseValidFile() throws Exception {
    String validCsvContent =
        "Customer,Total loan,Interest,Years\n" + "Alice,1000.0,5.0,2\n" + "Bob,2000.0,3.0,3";
    Reader reader = new StringReader(validCsvContent);

    List<CustomerLoanInfo> result = service.parseFile(reader);

    CustomerLoanInfo alice = new CustomerLoanInfo("Alice", 1000.0, 5.0, 2);
    CustomerLoanInfo bob = new CustomerLoanInfo("Bob", 2000.0, 3.0, 3);
    assertEquals(2, result.size());
    assertEquals(alice, result.get(0));
    assertEquals(bob, result.get(1));
    reader.close();
  }

  @Test
  void testParseInvalidFileFormat() throws IOException {
    String invalidCsvContent =
        "Customer,Total loan,Interest\n" + "Alice,1000.0,5.0\n" + "Bob,2000.0,3.0";
    Reader reader = new StringReader(invalidCsvContent);

    List<CustomerLoanInfo> result = service.parseFile(reader);
    assertEquals(0, result.size());
    reader.close();
  }

  @Test
  void testParseInvalidNumberFormat() throws IOException {
    String invalidCsvContent =
        "Customer,Total loan,Interest\n" + "Alice,1000.0,abc,5\n" + "Bob,2000.0,abc,3";
    Reader reader = new StringReader(invalidCsvContent);

    assertThrows(InvalidNumberFormatException.class, () -> service.parseFile(reader));
    reader.close();
  }

  @Test
  void testParseEmptyFile() throws Exception {
    String mockEmptyFile = "";
    Reader reader = new StringReader(mockEmptyFile);

    List<CustomerLoanInfo> result = service.parseFile(reader);

    assertTrue(result.isEmpty());
    reader.close();
  }
}
