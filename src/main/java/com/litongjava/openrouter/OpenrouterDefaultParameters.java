package com.litongjava.openrouter;

public class OpenrouterDefaultParameters {

  private Object temperature;
  private Object top_p;
  private Object frequency_penalty;

  public OpenrouterDefaultParameters() {
  }

  public OpenrouterDefaultParameters(Object temperature, Object top_p, Object frequency_penalty) {
    this.temperature = temperature;
    this.top_p = top_p;
    this.frequency_penalty = frequency_penalty;
  }

  public Object getTemperature() {
    return temperature;
  }

  public void setTemperature(Object temperature) {
    this.temperature = temperature;
  }

  public Object getTop_p() {
    return top_p;
  }

  public void setTop_p(Object top_p) {
    this.top_p = top_p;
  }

  public Object getFrequency_penalty() {
    return frequency_penalty;
  }

  public void setFrequency_penalty(Object frequency_penalty) {
    this.frequency_penalty = frequency_penalty;
  }
}
