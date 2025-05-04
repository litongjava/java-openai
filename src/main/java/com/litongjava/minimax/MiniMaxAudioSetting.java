package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniMaxAudioSetting {
  private int sample_rate=32000;
  private int bitrate=128000;
  private String format="mp3";
  private int channel=1;
}
