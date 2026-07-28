package nexus.io.bailian.tts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BailianTTSOutput {
  private BailianTTSOutputAudio audio;
  private String finish_reason;
}
