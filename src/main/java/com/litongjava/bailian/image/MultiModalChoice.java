package com.litongjava.bailian.image;

public class MultiModalChoice {
  private String finish_reason;
  private MultiModalMessage message;

  public MultiModalChoice() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalChoice(String finish_reason, MultiModalMessage message) {
    super();
    this.finish_reason = finish_reason;
    this.message = message;
  }

  public String getFinish_reason() {
    return finish_reason;
  }

  public void setFinish_reason(String finish_reason) {
    this.finish_reason = finish_reason;
  }

  public MultiModalMessage getMessage() {
    return message;
  }

  public void setMessage(MultiModalMessage message) {
    this.message = message;
  }

}
