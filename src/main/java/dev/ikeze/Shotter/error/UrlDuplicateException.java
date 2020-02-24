package dev.ikeze.Shotter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UrlDuplicateException extends RuntimeException {

  public UrlDuplicateException(String directory) {
    super("Url is a duplicate : " + directory);
  }
}