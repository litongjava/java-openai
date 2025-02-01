package com.litongjava.jian.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JinaSearchRequest {
  // 请求URL
  private String endpoint;
  // 请求方法
  private String method;

  // Headers
  private String authorization;
  private String contentType;
  private String xRetainImages;
  private String xSite;
  private String xWithGeneratedAlt;
  private String xWithLinksSummary;

  // 请求体中的字段
  private String q;
  private String count;

  public void setCount(int count) {
    this.count = String.valueOf(count);
  }
}
