package com.litongjava.searxng;

import lombok.Data;
import java.util.List;

@Data
public class SearxngSearchResponse {
  private String query;
  private int number_of_results;
  private List<SearxngResult> results;
  private List<Object> answers;
  private List<Object> corrections;
  private List<Object> infoboxes;
  private List<String> suggestions;
  private List<Object> unresponsive_engines;
}
