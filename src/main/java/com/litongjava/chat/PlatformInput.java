package com.litongjava.chat;

public class PlatformInput {

  private String platform;
  private String model;

  public PlatformInput(String platform) {
    this.platform = platform;
  }

  public PlatformInput() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PlatformInput(String platform, String model) {
    super();
    this.platform = platform;
    this.model = model;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

}
