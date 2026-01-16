package com.litongjava.groq;

public class TranscriptionsRequest {
  private String model = "whisper-large-v3-turbo";
  private Float temperature;
  private String response_format = "json";
  private String language;

  public TranscriptionsRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public TranscriptionsRequest(String model, Float temperature, String response_format, String language) {
    super();
    this.model = model;
    this.temperature = temperature;
    this.response_format = response_format;
    this.language = language;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Float getTemperature() {
    return temperature;
  }

  public void setTemperature(Float temperature) {
    this.temperature = temperature;
  }

  public String getResponse_format() {
    return response_format;
  }

  public void setResponse_format(String response_format) {
    this.response_format = response_format;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
