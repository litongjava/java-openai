package com.litongjava.searxng;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class SearxngSearchParam {
  private String q; // 搜索关键词
  private String format; // 返回格式，例如 json、rss 等
  private String language; // 语言，使用 ISO 639-1 代码（如 "en" 或 "zh"）
  private String categories; // 搜索类别，例如 general、news、images 等
  private String engines; // 指定搜索引擎（如 google、bing、duckduckgo）
  private Integer pageno; // 页码，默认为 1
  private String time_range; // 时间范围，例如 "day"、"week"、"month"
  private Integer safesearch; // 安全搜索，0 为关闭，1 为开启
  private String autocomplete; // 自动补全（true/false）
  private String locale; // 区域设置（如 "US" 或 "CN"）
  private Boolean no_cache; // 是否禁用缓存（true/false）
  private String theme; // 界面主题（如 "light"、"dark"）
}
