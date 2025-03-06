package com.litongjava.searchapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiInformation {
  private String query_displayed;
  private int total_results;
  private double time_taken_displayed;
  private String detected_location;
}
