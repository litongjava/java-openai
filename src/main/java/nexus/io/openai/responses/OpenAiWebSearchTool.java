package nexus.io.openai.responses;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import nexus.io.search.WebSearchUserLocation;

/** Hosted web search for POST /responses. */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OpenAiWebSearchTool extends OpenAiResponsesTool {
  private String search_context_size;
  private Boolean external_web_access;
  private Filters filters;
  private WebSearchUserLocation user_location;

  public OpenAiWebSearchTool() {
    super("web_search");
  }

  @Data
  @Accessors(chain = true)
  public static class Filters {
    private List<String> allowed_domains;
    private List<String> blocked_domains;
  }
}
