package nexus.io.openai.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesOutput {
  private String id;
  private String type;
  private String role;
  private String status;
  private List<OpenAiResponsesOutputContent> content;
  private List<OpenAiResponsesSummary> summary;
}
