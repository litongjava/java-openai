package com.litongjava.tavily;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TavilyExtractRequest {
  /**
   * 待抽取内容的 URL，支持单个或多个（根据实际需求，多个 URL 可改为 List<String>）
   */
  private String urls;

  /**
   * 是否需要抽取图片
   */
  private Boolean include_images;

  /**
   * 抽取深度，如 "basic"、"full" 等
   */
  private String extract_depth;
}
