package com.litongjava.byteplus;

public class BytePlusTTSResponse {
  private int code;
  private String message;
  private String data;
  private BytePlusSentence sentence;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public BytePlusSentence getSentence() {
    return sentence;
  }

  public void setSentence(BytePlusSentence sentence) {
    this.sentence = sentence;
  }
}
