package com.litongjava.gemini; // Adjust package as needed

import java.util.List;

/**
 * 响应体 for GET /v1beta/cachedContents
 */

public class GeminiListCachesResponse {
  private List<GeminiCacheVo> cachedContents;

  public GeminiListCachesResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiListCachesResponse(List<GeminiCacheVo> cachedContents) {
    super();
    this.cachedContents = cachedContents;
  }

  public List<GeminiCacheVo> getCachedContents() {
    return cachedContents;
  }

  public void setCachedContents(List<GeminiCacheVo> cachedContents) {
    this.cachedContents = cachedContents;
  }

}