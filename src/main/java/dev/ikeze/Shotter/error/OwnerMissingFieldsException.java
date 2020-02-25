package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OwnerMissingFieldsException extends RuntimeException {

  private static final long serialVersionUID = -1228987392559761544L;

  public OwnerMissingFieldsException() {
    super("Please provide email, name and password fields");
  }
}