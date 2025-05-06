package com.litongjava.gemini; // Adjust package as needed

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示 cache 的 usageMetadata 结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCacheUsageMetadataVo {
  private Integer totalTokenCount;
}