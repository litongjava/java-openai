package nexus.io.chat;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import nexus.io.search.WebSearchUserLocation;

/** Options translated to the selected provider's native search schema. */
@Data
@Accessors(chain = true)
public class UniWebSearchOptions {
  /** OpenAI max_tool_calls; Anthropic/DeepSeek max_uses. */
  private Integer maxToolCalls = 5;
  private List<String> allowedDomains;
  private List<String> blockedDomains;
  private WebSearchUserLocation userLocation;
  /** OpenAI only: low, medium, high. */
  private String searchContextSize;
  /** OpenAI only: false restricts search to cached/indexed results. */
  private Boolean externalWebAccess;
}
