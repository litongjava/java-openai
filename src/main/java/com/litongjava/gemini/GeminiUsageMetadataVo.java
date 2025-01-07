package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UsageMetadata
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiUsageMetadataVo {
  private int promptTokenCount;
  private int candidatesTokenCount;
  private int totalTokenCount;
}
