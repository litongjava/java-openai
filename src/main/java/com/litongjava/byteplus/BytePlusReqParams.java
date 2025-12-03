package com.litongjava.byteplus;

public class BytePlusReqParams {
  private String text;
  private String model;
  private String speaker;
  private BytePlusAudioParams audio_params;
  // 注意：文档要求 additions 是 jsonstring，这里用 String
  private String additions;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSpeaker() {
    return speaker;
  }

  public void setSpeaker(String speaker) {
    this.speaker = speaker;
  }

  public BytePlusAudioParams getAudio_params() {
    return audio_params;
  }

  public void setAudio_params(BytePlusAudioParams audio_params) {
    this.audio_params = audio_params;
  }

  public String getAdditions() {
    return additions;
  }

  public void setAdditions(String additions) {
    this.additions = additions;
  }
}
