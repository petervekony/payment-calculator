package com.crosskey.mortgage.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.core.exception.FileEmptyException;
import com.crosskey.mortgage.core.exception.FileSizeLimitExceededException;
import com.crosskey.mortgage.core.exception.UnsupportedFileTypeException;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.service.CoreCalculatorService;

class CalculatorServiceTest {

  @Mock private CoreCalculatorService coreCalculatorService;

  @InjectMocks private CalculatorService calculatorService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testParseFileSuccess() throws Exception {
    MultipartFile file =
        new MockMultipartFile("file", "filename.txt", "text/plain", "some data".getBytes());
    List<CustomerLoanInfo> mockInfoList = new ArrayList<>();
    mockInfoList.add(new CustomerLoanInfo());
    when(coreCalculatorService.parseFile(any())).thenReturn(mockInfoList);

    List<CustomerLoanInfo> result = calculatorService.parseFile(file);
    assertEquals(1, result.size());
  }

  @Test
  void testParseFileEmpty() {
    MultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);
    assertThrows(FileEmptyException.class, () -> calculatorService.parseFile(emptyFile));
  }

  @Test
  void testParseFileUnsupportedFileType() {
    MultipartFile wrongTypeFile =
        new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);
    assertThrows(
        UnsupportedFileTypeException.class, () -> calculatorService.parseFile(wrongTypeFile));
  }

  @Test
  void testParseFileSizeLimitExceeded() throws IOException {
    byte[] largeFileContent = new byte[1048577];
    ByteArrayInputStream largeFileInputStream = new ByteArrayInputStream(largeFileContent);
    MultipartFile largeFile =
        new MockMultipartFile("file", "filename.txt", "text/plain", largeFileInputStream);

    assertThrows(
        FileSizeLimitExceededException.class, () -> calculatorService.parseFile(largeFile));
  }
}
