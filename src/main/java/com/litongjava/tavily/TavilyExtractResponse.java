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
public class TavilyExtractResponse {
  /**
   * 成功抽取结果列表
   */
  private List<TavilyExtractResult> results;

  /**
   * 抽取失败的 URL 列表
   */
  private List<String> failed_results;

  /**
   * 响应耗时（单位秒）
   */
  private Double response_time;
}
