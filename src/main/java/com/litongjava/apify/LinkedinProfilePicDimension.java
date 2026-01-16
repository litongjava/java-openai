package com.litongjava.apify;

public class LinkedinProfilePicDimension {
  private Integer width;
  private Integer height;
  private String url;

  public LinkedinProfilePicDimension() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LinkedinProfilePicDimension(Integer width, Integer height, String url) {
    super();
    this.width = width;
    this.height = height;
    this.url = url;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
