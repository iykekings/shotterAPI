package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public OwnerNotFoundException(String id) {
    super("Owner not found: " + id);
  }
}