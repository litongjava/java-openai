package com.litongjava.searchapi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiOrganicResult {
  private int position;
  private String title;
  private String link;
  private String source;
  private String domain;
  private String displayed_link;
  private String snippet;
  private List<String> snippet_highlighted_words;
  private String date;
  private String favicon;
  private String thumbnail;
}
