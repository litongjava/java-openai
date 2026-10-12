package nexus.io.claude;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import nexus.io.openai.chat.ChatRequestTool;
import nexus.io.search.WebSearchUserLocation;

/** Server-side search for Anthropic Messages and DeepSeek's compatible endpoint. */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClaudeWebSearchTool extends ChatRequestTool {
  public static final String BASIC_VERSION = "web_search_20250305";

  private String name = "web_search";
  private Integer max_uses;
  private List<String> allowed_domains;
  private List<String> blocked_domains;
  private WebSearchUserLocation user_location;

  public ClaudeWebSearchTool() {
    super(BASIC_VERSION, null);
  }
}
