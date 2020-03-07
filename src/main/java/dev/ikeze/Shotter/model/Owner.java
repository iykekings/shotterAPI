package dev.ikeze.Shotter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID ownerid;

  @Column(unique = true)
  private String email;

  private String name;

  private String password;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("owner")
  private List<Url> urls = new ArrayList<>();

  public Owner(String name, String email, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public Owner() {
  }

  public List<Url> getUrls() {
    return urls;
  }

  public void setUrls(List<Url> urls) {
    this.urls = urls;
  }

  public void setOwnerid(UUID ownerid) {
    this.ownerid = ownerid;
  }

  public UUID getOwnerid() {
    return ownerid;
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
    return String.format("Id: %d, name: %s, email: %s, password: %s", ownerid, name, email, password);
  }
}