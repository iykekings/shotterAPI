package dev.ikeze.Shotter.model;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private String username;
  private String password;

  public AuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AuthenticationRequest() {
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return String.format("Username: %s, Password: %s", username, password);
  }
}