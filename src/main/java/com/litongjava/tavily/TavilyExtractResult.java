package com.litongjava.tavily;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TavilyExtractResult {
  /**
   * 内容来源 URL
   */
  private String url;

  /**
   * 原始抽取内容
   */
  private String raw_content;

  /**
   * 图片列表
   */
  private List<String> images;
}
