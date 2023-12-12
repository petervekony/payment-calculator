package com.crosskey.mortgage.web.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.mortgage.core.exception.FileEmptyException;
import com.crosskey.mortgage.core.exception.FileSizeLimitExceededException;
import com.crosskey.mortgage.core.model.CustomerLoanInfo;
import com.crosskey.mortgage.core.service.CoreCalculatorService;

@Service
public class CalculatorService {

  private final CoreCalculatorService coreCalculatorService;

  @Autowired
  public CalculatorService(CoreCalculatorService coreCalculatorService) {
    this.coreCalculatorService = coreCalculatorService;
  }

  public List<CustomerLoanInfo> parseFile(MultipartFile file)
      throws FileEmptyException, IOException {
    if (file.isEmpty()) {
      throw new FileEmptyException("Error: file is empty");
    }

    if (file.getSize() > 1048576) {
      throw new FileSizeLimitExceededException("Error: file size limit (1MB) exceeded");
    }

    BufferedReader reader =
        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

    return coreCalculatorService.parseFile(reader);
  }
}