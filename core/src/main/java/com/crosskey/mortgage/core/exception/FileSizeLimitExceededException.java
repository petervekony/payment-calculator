package com.crosskey.mortgage.core.exception;

public class FileSizeLimitExceededException extends RuntimeException {
  public FileSizeLimitExceededException(String message) {
    super(message);
  }
}
