package nexus.io.openai.responses;

import java.util.List;

import lombok.Data;

/** A search, open_page, or find_in_page action in a web_search_call output item. */
@Data
public class OpenAiWebSearchAction {
  private String type;
  private String query;
  private List<String> queries;
  private String url;
  private String pattern;
  private List<Source> sources;

  @Data
  public static class Source {
    private String type;
    private String url;
    private String title;
  }
}
