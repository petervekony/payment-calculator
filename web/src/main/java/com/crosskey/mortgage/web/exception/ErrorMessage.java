package com.crosskey.mortgage.web.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
  private String message;
  private int status;

  public ErrorMessage(String message, HttpStatus status) {
    this.message = message;
    this.status = status.value();
  }
}
