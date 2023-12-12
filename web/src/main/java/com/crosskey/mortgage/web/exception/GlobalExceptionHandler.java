package com.crosskey.mortgage.web.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crosskey.mortgage.core.exception.FileEmptyException;
import com.crosskey.mortgage.core.exception.FileSizeLimitExceededException;
import com.crosskey.mortgage.core.exception.InvalidNumberFormatException;
import com.crosskey.mortgage.core.exception.UnsupportedFileTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOException(IOException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnsupportedFileTypeException.class)
  public ResponseEntity<String> handleUnsupportedFileTypeException(UnsupportedFileTypeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FileSizeLimitExceededException.class)
  public ResponseEntity<String> handleFileSizeLimitExceededException(
      FileSizeLimitExceededException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FileEmptyException.class)
  public ResponseEntity<String> handleFileEmptyException(FileEmptyException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidNumberFormatException.class)
  public ResponseEntity<String> handleInvalidNumberFormatException(InvalidNumberFormatException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
