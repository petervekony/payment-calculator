package com.crosskey.mortgage.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.core.exception.FileEmptyException;
import com.crosskey.mortgage.core.exception.FileSizeLimitExceededException;
import com.crosskey.mortgage.core.exception.UnsupportedFileTypeException;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.web.service.CalculatorService;

public class CalculatorControllerTests {
  @Mock private CalculatorService calculatorService;

  @InjectMocks private CalculatorController calculatorController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void calculateMonthlyPayments_ReturnsOk() throws Exception {
    MultipartFile file =
        new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());

    ResponseEntity<List<CustomerLoanInfo>> response =
        calculatorController.calculateMonthlyPayments(file);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void calculateMonthlyPayments_ThrowsFileEmptyException() throws IOException {
    MultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", new byte[0]);
    when(calculatorService.parseFile(any(MultipartFile.class)))
        .thenThrow(new FileEmptyException("Error: file is empty"));

    Exception exception =
        assertThrows(
            FileEmptyException.class,
            () -> {
              calculatorController.calculateMonthlyPayments(file);
            });

    assertTrue(exception.getMessage().contains("Error: file is empty"));
  }

  @Test
  void calculateMonthlyPayments_ThrowsFileSizeLimitExceededException() throws Exception {
    byte[] largeFileContent = new byte[1048577]; // 1MB + 1 byte
    MockMultipartFile file =
        new MockMultipartFile("file", "largefile.txt", "text/plain", largeFileContent);

    when(calculatorService.parseFile(any(MultipartFile.class)))
        .thenThrow(new FileSizeLimitExceededException("Error: file size limit (1MB) exceeded"));

    Exception exception =
        assertThrows(
            FileSizeLimitExceededException.class,
            () -> calculatorController.calculateMonthlyPayments(file));
    assertTrue(exception.getMessage().contains("Error: file size limit (1MB) exceeded"));
  }

  @Test
  void calculateMonthlyPayments_ThrowsUnsupportedFileTypeException() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("file", "filename.pdf", "application/pdf", "content".getBytes());

    when(calculatorService.parseFile(any(MultipartFile.class)))
        .thenThrow(new UnsupportedFileTypeException("Error: unsupported file type"));

    Exception exception =
        assertThrows(
            UnsupportedFileTypeException.class,
            () -> calculatorController.calculateMonthlyPayments(file));
    assertTrue(exception.getMessage().contains("Error: unsupported file type"));
  }
}
