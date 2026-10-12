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
  private OpenAiWebSearchAction action;

  public OpenAiResponsesOutput(String id, String type, String role, String status,
      List<OpenAiResponsesOutputContent> content, List<OpenAiResponsesSummary> summary) {
    this.id = id;
    this.type = type;
    this.role = role;
    this.status = status;
    this.content = content;
    this.summary = summary;
  }
}
