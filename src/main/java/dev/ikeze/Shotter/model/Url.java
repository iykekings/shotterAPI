package dev.ikeze.Shotter.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID Id;

  private String directory;
  private String redirect;
  private int clicks;
  private String description;
  private String image;
  private String title;

  @ManyToOne(optional = true)
  @JoinColumn(name = "ownerid", updatable = false)
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

  public UUID getId() {
    return Id;
  }

  public int getClicks() {
    return clicks;
  }

  public void setClicks(int clicks) {
    this.clicks = clicks;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setId(UUID Id) {
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
    return "Id: " + Id + ", directory: " + directory + ", redirect: " + redirect + ", clicks: " + clicks;
  }

}