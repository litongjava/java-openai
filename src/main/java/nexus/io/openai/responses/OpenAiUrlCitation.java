package nexus.io.openai.responses;

import lombok.Data;

/** URL citation in a Responses output_text block. Indices refer to that block. */
@Data
public class OpenAiUrlCitation {
  private String type;
  private String url;
  private String title;
  private Integer start_index;
  private Integer end_index;
}
