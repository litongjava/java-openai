package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://platform.minimax.io/document/T2A%20V2
 * 
 * @author Tong Li
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MiniMaxTTSRequest {
  private String model = "speech-2.6-hd";
  private String text;
  private boolean stream = false;
  private String language_boost;
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

  public MiniMaxTTSRequest(String text, String voice, String language_boost) {
    this.text = text;
    this.voice_setting = new MiniMaxVoiceSetting(voice);
    this.language_boost = language_boost;
    this.audio_setting = new MiniMaxAudioSetting();
  }
}
