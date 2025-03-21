package com.litongjava.tavily;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TavilySearchRequest {
  private String query;

  private String topic;

  private String search_depth;

  private Integer chunks_perSource;

  private Integer max_results;

  private Object time_range;

  private Integer days;

  private Boolean include_answer;

  private Boolean include_raw_content;

  private Boolean include_images;

  private Boolean include_image_descriptions;

  private List<String> include_domains;

  private List<String> exclude_domains;
}
