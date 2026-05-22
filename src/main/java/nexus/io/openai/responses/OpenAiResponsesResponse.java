package nexus.io.openai.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesResponse {
  private String id;
  private String object;
  private Long created_at;
  private String model;
  private Integer max_output_tokens;
  private String service_tier;
  private String status;
  private List<OpenAiResponsesOutput> output;
  private OpenAiResponsesUsage usage;
  private OpenAiResponsesCaching caching;
  private Boolean store;
  private Long expire_at;
  private String rawResponse;

  public String getOutputText() {
    if (output == null || output.isEmpty()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (OpenAiResponsesOutput item : output) {
      if (item == null || item.getContent() == null) {
        continue;
      }
      for (OpenAiResponsesOutputContent content : item.getContent()) {
        if (content != null && "output_text".equals(content.getType()) && content.getText() != null) {
          sb.append(content.getText());
        }
      }
    }
    return sb.length() == 0 ? null : sb.toString();
  }
}
