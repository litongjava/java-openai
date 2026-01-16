package com.litongjava.bailian.image;

public class MultiModalUsage {
  private Integer height, width, image_count;

  public MultiModalUsage() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalUsage(Integer height, Integer width, Integer image_count) {
    super();
    this.height = height;
    this.width = width;
    this.image_count = image_count;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getImage_count() {
    return image_count;
  }

  public void setImage_count(Integer image_count) {
    this.image_count = image_count;
  }

}
