package com.litongjava.searchapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiSource {
  private String title;
  private String link;
  private String source;
  private String domain;
  private String displayed_link;
  private String favicon;
}
