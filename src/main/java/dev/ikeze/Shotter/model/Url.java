package dev.ikeze.Shotter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long Id;

  private String directory;

  public Url(String directory) {
    this.directory = directory;
  }

  public Url() {
  }

  public String getDirectory() {
    return directory;
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

  @Override
  public String toString() {
    return "Id: " + Id + ", directory: " + directory;
  }

}