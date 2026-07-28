package nexus.io.bailian.tts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BailianTTSInput {
  private String voice;
  private String text;
  private String format;
  private Integer sample_rate;

  public BailianTTSInput(String voice, String text) {
    this.voice = voice;
    this.text = text;
    this.format = "mp3";
    this.sample_rate = 16000;
  }
}
