package dev.ikeze.Shotter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long Id;

  private String directory;
  private String redirect;

  @ManyToOne
  @JoinColumn(name = "ownerid", nullable = false)
  @JsonIgnoreProperties({ "urls", "password" })
  private Owner owner;

  public Url(String directory, String redirect, Owner owner) {
    this.directory = directory;
    this.redirect = redirect;
    this.owner = owner;
  }

  public Url() {
  }

  public Owner getOwner() {
    return this.owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public String getDirectory() {
    return directory;
  }

  public String getRedirect() {
    return redirect;
  }

  public long getid() {
    return Id;
  }

  public void setId(long Id) {
    this.Id = Id;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  @Override
  public String toString() {
    return "Id: " + Id + ", directory: " + directory;
  }

}