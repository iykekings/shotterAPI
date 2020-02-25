package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UrlMissingFieldsException extends RuntimeException {

  private static final long serialVersionUID = -1228987392559761544L;

  public UrlMissingFieldsException() {
    super("Please provide directory, redirect and/or owner.ownerid fields");
  }
}