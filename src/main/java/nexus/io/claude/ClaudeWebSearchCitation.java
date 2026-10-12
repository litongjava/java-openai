package nexus.io.claude;

import lombok.Data;

@Data
public class ClaudeWebSearchCitation {
  private String type;
  private String url;
  private String title;
  private String cited_text;
  /** Preserve this value unchanged when continuing a conversation. */
  private String encrypted_index;
}
