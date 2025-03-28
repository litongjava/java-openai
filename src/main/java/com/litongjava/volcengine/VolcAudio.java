package com.litongjava.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcAudio {
  private String voice_type;
  private String voice;
  private String encoding;
  private Double speed_ratio;
  private Double volume_ratio;
  private Double pitch_ratio;
  private String emotion;
  private String language;
}