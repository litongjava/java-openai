package nexus.io.claude;

import lombok.Data;

@Data
public class ClaudeWebSearchResult {
  private String type;
  private String url;
  private String title;
  private String page_age;
  /** Preserve this value unchanged when continuing a conversation. */
  private String encrypted_content;
}
