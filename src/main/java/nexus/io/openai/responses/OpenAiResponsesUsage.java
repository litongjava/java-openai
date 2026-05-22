package nexus.io.openai.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesUsage {
  private Integer input_tokens;
  private Integer output_tokens;
  private Integer total_tokens;
  private OpenAiResponsesInputTokensDetails input_tokens_details;
  private OpenAiResponsesOutputTokensDetails output_tokens_details;
}
