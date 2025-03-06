package com.litongjava.searchapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiMetadata {
  private String id;
  private String status;
  private String created_at;
  private double request_time_taken;
  private double parsing_time_taken;
  private double total_time_taken;
  private String request_url;
  private String html_url;
  private String json_url;
}
