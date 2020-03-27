package dev.ikeze.Shotter.model;

public class Exists {
  private boolean state;

  public Exists(boolean state) {
    this.state = state;
  }

  public boolean getState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }
}