package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrlNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1700014759880456200L;

  public UrlNotFoundException(String id) {
    super("Url not found : " + id);
  }
}