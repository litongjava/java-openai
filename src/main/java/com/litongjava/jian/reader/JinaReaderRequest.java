package com.litongjava.jian.reader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JinaReaderRequest {
  // 请求URL
  private String endpoint;
  // Headers
  private String authorization;
  private String xRespondWith;
  private String contentType;
  private String xRetainImages;
  private String xWithGeneratedAlt;
  private String xWithLinksSummary;

  private String url;
}
