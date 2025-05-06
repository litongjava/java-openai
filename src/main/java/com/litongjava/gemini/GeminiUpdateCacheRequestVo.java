package com.litongjava.gemini; // Adjust package as needed

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求体 for PATCH /v1beta/{cache_name}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiUpdateCacheRequestVo {
  private String ttl; // e.g., "600s"
  private String expireTime; // Alternative to ttl

  public GeminiUpdateCacheRequestVo(String ttl) {
    this.ttl = ttl;
  }
}