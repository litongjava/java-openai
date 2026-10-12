package nexus.io.openai.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiResponsesRequest {
  private String model;
  private List<OpenAiResponsesInput> input;
  private Integer max_output_tokens;
  private Float temperature;
  private Float top_p;
  private Boolean stream;
  private Boolean store;
  private List<OpenAiResponsesTool> tools;
  /** "auto", "required", "none", or a provider-specific tool choice object. */
  private Object tool_choice;
  private List<String> include;

  /** Retains the original constructor for existing callers. */
  public OpenAiResponsesRequest(String model, List<OpenAiResponsesInput> input, Integer max_output_tokens,
      Float temperature, Float top_p, Boolean stream, Boolean store) {
    this.model = model;
    this.input = input;
    this.max_output_tokens = max_output_tokens;
    this.temperature = temperature;
    this.top_p = top_p;
    this.stream = stream;
    this.store = store;
  }
}
