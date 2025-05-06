package com.litongjava.gemini; // Adjust package as needed

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示一个缓存条目的元数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCacheVo {
  private String name; // Format: cachedContents/{cache_id}
  private String model;
  private String displayName;
  private String createTime;
  private String updateTime;
  private String expireTime;
  private GeminiCacheUsageMetadataVo usageMetadata;
}