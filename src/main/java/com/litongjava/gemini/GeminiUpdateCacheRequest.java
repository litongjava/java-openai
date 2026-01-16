package com.litongjava.gemini; // Adjust package as needed

/**
 * 请求体 for PATCH /v1beta/{cache_name}
 */
public class GeminiUpdateCacheRequest {
  private String ttl; // e.g., "600s"
  private String expireTime; // Alternative to ttl

  public GeminiUpdateCacheRequest(String ttl) {
    this.ttl = ttl;
  }

  public GeminiUpdateCacheRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiUpdateCacheRequest(String ttl, String expireTime) {
    super();
    this.ttl = ttl;
    this.expireTime = expireTime;
  }

  public String getTtl() {
    return ttl;
  }

  public void setTtl(String ttl) {
    this.ttl = ttl;
  }

  public String getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = expireTime;
  }

}