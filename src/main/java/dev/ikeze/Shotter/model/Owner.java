package dev.ikeze.Shotter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long Id;

  private String email;
  private String name;
  private String password;

  public Owner(long Id, String email, String name, String password) {
    this.Id = Id;
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public Owner() {
  }

  public void setId(long Id) {
    this.Id = Id;
  }

  public long getId() {
    return Id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return String.format("Id: %d, name: %s, email: %s, password: %s", Id, name, email, password);
  }
}