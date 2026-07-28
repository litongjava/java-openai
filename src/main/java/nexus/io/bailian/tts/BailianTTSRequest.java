package nexus.io.bailian.tts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BailianTTSRequest {
  private String model;
  private BailianTTSInput input;

  public BailianTTSRequest(String model, String voice, String text) {
    this.model = model;
    this.input = new BailianTTSInput(voice, text);
  }
}
