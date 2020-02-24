package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;

public class ApiError {
  private String message;
  private HttpStatus status;

  public ApiError(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return status;
  }
}