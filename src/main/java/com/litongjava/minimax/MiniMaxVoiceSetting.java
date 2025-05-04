package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniMaxVoiceSetting {
  private String voice_id;
  private int speed = 1;
  private int vol = 1;
  private int pitch = 0;

  public MiniMaxVoiceSetting(String voice) {
    this.voice_id = voice;
  }
}
