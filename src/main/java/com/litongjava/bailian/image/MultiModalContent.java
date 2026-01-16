package com.litongjava.bailian.image;

public class MultiModalContent {

  private String text;
  private String image;

  public MultiModalContent(String text) {
    this.text = text;
  }

  public MultiModalContent() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalContent(String text, String image) {
    super();
    this.text = text;
    this.image = image;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
