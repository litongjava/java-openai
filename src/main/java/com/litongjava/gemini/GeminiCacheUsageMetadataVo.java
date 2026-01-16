package com.litongjava.gemini; // Adjust package as needed

/**
 * 表示 cache 的 usageMetadata 结构
 */
public class GeminiCacheUsageMetadataVo {
  private Integer totalTokenCount;

  public GeminiCacheUsageMetadataVo() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiCacheUsageMetadataVo(Integer totalTokenCount) {
    super();
    this.totalTokenCount = totalTokenCount;
  }

  public Integer getTotalTokenCount() {
    return totalTokenCount;
  }

  public void setTotalTokenCount(Integer totalTokenCount) {
    this.totalTokenCount = totalTokenCount;
  }

}