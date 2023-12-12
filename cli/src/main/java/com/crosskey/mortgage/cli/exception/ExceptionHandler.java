package com.crosskey.mortgage.cli.exception;

public class ExceptionHandler {
  private ExceptionHandler() {}

  public static void handleException(Exception e) {
    System.err.println(e.getMessage());
  }
}
