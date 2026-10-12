package nexus.io.openai.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/** Base for Responses API tools. Chat Completions tools use a different schema. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponsesTool {
  private String type;
}
