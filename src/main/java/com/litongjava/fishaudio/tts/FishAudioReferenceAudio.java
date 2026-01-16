package com.litongjava.fishaudio.tts;

/**
 * 表示参考音频，用于 in-context 学习。
 */

public class FishAudioReferenceAudio {
  // 二进制格式的参考音频数据
  private byte[] audio;
  // 参考音频对应的文字描述
  private String text;

  public FishAudioReferenceAudio() {
    super();
    // TODO Auto-generated constructor stub
  }

  public FishAudioReferenceAudio(byte[] audio, String text) {
    super();
    this.audio = audio;
    this.text = text;
  }

  public byte[] getAudio() {
    return audio;
  }

  public void setAudio(byte[] audio) {
    this.audio = audio;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
