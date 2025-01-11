package com.litongjava.searxng;

import lombok.Data;
import java.util.List;

@Data
public class SearxngResult {
  private String url;
  private String title;
  private String content;
  private String thumbnail;
  private String engine;
  private List<String> parsed_url;
  private String template;
  private List<String> engines;
  private List<Integer> positions;
  private String publishedDate; // 如果日期需要特别处理，可改为日期类型
  private double score;
  private String category;
}
