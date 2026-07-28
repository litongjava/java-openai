package nexus.io.bailian.tts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BailianTTSResponse {
  private BailianTTSOutput output;
  private BailianTTSUsage usage;
  private String request_id;
}
