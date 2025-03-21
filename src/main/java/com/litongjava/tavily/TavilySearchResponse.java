package com.litongjava.tavily;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TavilySearchResponse {
  private String query;
  private String answer;
  private List<String> images;
  private List<TavilySearchResult> results;
  private String response_time;
}
