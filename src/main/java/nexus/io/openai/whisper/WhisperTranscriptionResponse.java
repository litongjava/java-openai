package nexus.io.openai.whisper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhisperTranscriptionResponse {
  private String text;
  private String rawResponse;

  public void setRasResponse(String rawResponse) {
    this.rawResponse = rawResponse;
  }
}
