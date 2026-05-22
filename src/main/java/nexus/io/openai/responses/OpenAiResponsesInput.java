package nexus.io.openai.responses;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiResponsesInput {
  private String role;
  private List<OpenAiResponsesInputContent> content;

  public static OpenAiResponsesInput userText(String text) {
    return new OpenAiResponsesInput("user", Collections.singletonList(OpenAiResponsesInputContent.text(text)));
  }

  public static OpenAiResponsesInput user(List<OpenAiResponsesInputContent> content) {
    return new OpenAiResponsesInput("user", content);
  }
}
