package nexus.io.bailian.tts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BailianTTSOutputAudio {
  private String id;
  private String url;
  private String data;
  private Integer expires_at;
}
