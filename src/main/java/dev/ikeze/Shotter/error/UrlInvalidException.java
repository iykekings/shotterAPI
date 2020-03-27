package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrlInvalidException extends RuntimeException {

  private static final long serialVersionUID = 1730014759880456200L;

  public UrlInvalidException(String url) {
    super("Url is invalid: " + url);
  }
}