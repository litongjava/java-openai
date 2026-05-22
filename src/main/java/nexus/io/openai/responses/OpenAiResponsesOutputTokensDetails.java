package nexus.io.openai.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesOutputTokensDetails {
  private Integer reasoning_tokens;
}
