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
  private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
  private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ErrorMessage> handleIOException(IOException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(UnsupportedFileTypeException.class)
  public ResponseEntity<ErrorMessage> handleUnsupportedFileTypeException(
      UnsupportedFileTypeException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(FileSizeLimitExceededException.class)
  public ResponseEntity<ErrorMessage> handleFileSizeLimitExceededException(
      FileSizeLimitExceededException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(FileEmptyException.class)
  public ResponseEntity<ErrorMessage> handleFileEmptyException(FileEmptyException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(InvalidNumberFormatException.class)
  public ResponseEntity<ErrorMessage> handleInvalidNumberFormatException(
      InvalidNumberFormatException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), BAD_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleNonSpecifiedExceptions(Exception e) {
    return new ResponseEntity<>(
        new ErrorMessage("Error: " + e.getMessage(), INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
  }
}
