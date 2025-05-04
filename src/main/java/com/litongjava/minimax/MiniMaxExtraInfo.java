package com.litongjava.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniMaxExtraInfo {
  private int audio_length;
  private int audio_sample_rate;
  private int audio_size;
  private int bitrate;
  private int word_count;
  private int invisible_character_ratio;
  private int usage_characters;
  private String format;
  private int audio_channel;
}
