package nexus.io.search;

import lombok.Data;
import lombok.experimental.Accessors;

/** Approximate location accepted by OpenAI and Anthropic web search. */
@Data
@Accessors(chain = true)
public class WebSearchUserLocation {
  private String type = "approximate";
  private String country;
  private String city;
  private String region;
  private String timezone;
}
