package com.litongjava.gemini; // Adjust package as needed

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应体 for GET /v1beta/cachedContents
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiListCachesResponse {
  private List<GeminiCacheVo> cachedContents;

}