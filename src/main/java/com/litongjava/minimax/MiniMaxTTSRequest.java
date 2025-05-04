package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MiniMaxTTSRequest {
  private String model = "speech-02-hd";
  private String text;
  private boolean stream = false;
  private boolean subtitle_enable = false;
  private MiniMaxVoiceSetting voice_setting;
  private MiniMaxAudioSetting audio_setting;

  public MiniMaxTTSRequest(String text) {
    this.text = text;
  }

  public MiniMaxTTSRequest(String text, String voice) {
    this.text = text;
    this.voice_setting = new MiniMaxVoiceSetting(voice);
    this.audio_setting = new MiniMaxAudioSetting();

  }
}
