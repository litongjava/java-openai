package nexus.io.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcAudio {
  private String voice_type;
  private String voice;
  private String encoding;
  private Integer rate;
  private Integer bitrate;
  private Double speed_ratio;
  private Double volume_ratio;
  private Double pitch_ratio;
  private Double loudness_ratio;
  private String emotion;
  private Boolean enable_emotion;
  private Double emotion_scale;
  private String language;
  private String explicit_language;
  private String context_language;
}
