package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OwnerDuplicateException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public OwnerDuplicateException(String email) {
    super(email + " already registered, try login in");
  }
}