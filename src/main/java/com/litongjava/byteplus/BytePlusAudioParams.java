package com.litongjava.byteplus;

public class BytePlusAudioParams {
  private String format;
  private Integer sample_rate;
  private Integer bit_rate;
  private String emotion;
  private Integer emotion_scale;
  private Integer speech_rate;
  private Integer loudness_rate;
  private Boolean enable_timestamp;

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Integer getSample_rate() {
    return sample_rate;
  }

  public void setSample_rate(Integer sample_rate) {
    this.sample_rate = sample_rate;
  }

  public Integer getBit_rate() {
    return bit_rate;
  }

  public void setBit_rate(Integer bit_rate) {
    this.bit_rate = bit_rate;
  }

  public String getEmotion() {
    return emotion;
  }

  public void setEmotion(String emotion) {
    this.emotion = emotion;
  }

  public Integer getEmotion_scale() {
    return emotion_scale;
  }

  public void setEmotion_scale(Integer emotion_scale) {
    this.emotion_scale = emotion_scale;
  }

  public Integer getSpeech_rate() {
    return speech_rate;
  }

  public void setSpeech_rate(Integer speech_rate) {
    this.speech_rate = speech_rate;
  }

  public Integer getLoudness_rate() {
    return loudness_rate;
  }

  public void setLoudness_rate(Integer loudness_rate) {
    this.loudness_rate = loudness_rate;
  }

  public Boolean getEnable_timestamp() {
    return enable_timestamp;
  }

  public void setEnable_timestamp(Boolean enable_timestamp) {
    this.enable_timestamp = enable_timestamp;
  }
}
