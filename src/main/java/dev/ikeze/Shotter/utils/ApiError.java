package dev.ikeze.Shotter.utils;

import org.springframework.http.HttpStatus;

public class ApiError {
  private String message;
  private HttpStatus httpStatus;

  public ApiError(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}