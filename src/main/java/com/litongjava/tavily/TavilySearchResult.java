package com.litongjava.tavily;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TavilySearchResult {
  private String title;
  private String url;
  private String content;
  private Double score;
  private String raw_content;
}
