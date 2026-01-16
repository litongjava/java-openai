package com.litongjava.gemini;

public class GroundingSupportSegment {
  private Integer endIndex;
  private String text;

  public GroundingSupportSegment() {
    super();
  }

  public GroundingSupportSegment(Integer endIndex, String text) {
    super();
    this.endIndex = endIndex;
    this.text = text;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
