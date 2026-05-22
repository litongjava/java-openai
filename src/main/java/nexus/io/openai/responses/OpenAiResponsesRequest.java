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
}
