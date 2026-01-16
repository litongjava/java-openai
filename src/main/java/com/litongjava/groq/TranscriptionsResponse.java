package com.litongjava.groq;

public class TranscriptionsResponse {
  private String text;
  private XGroq x_groq;

  public TranscriptionsResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public TranscriptionsResponse(String text, XGroq x_groq) {
    super();
    this.text = text;
    this.x_groq = x_groq;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public XGroq getX_groq() {
    return x_groq;
  }

  public void setX_groq(XGroq x_groq) {
    this.x_groq = x_groq;
  }

}
