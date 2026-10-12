package nexus.io.openai.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesOutputContent {
  private String type;
  private String text;
  private List<OpenAiUrlCitation> annotations;

  public OpenAiResponsesOutputContent(String type, String text) {
    this.type = type;
    this.text = text;
  }
}
