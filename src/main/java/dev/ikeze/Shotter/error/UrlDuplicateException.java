package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UrlDuplicateException extends RuntimeException {

  private static final long serialVersionUID = -3670278356021712338L;

  public UrlDuplicateException(String directory) {
    super("Url is a duplicate: " + directory);
  }
}