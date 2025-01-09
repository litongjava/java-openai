package com.litongjava.gemini;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * gemini接口响应体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiChatResponseVo {
  private List<GeminiCandidateVo> candidates;
  private GeminiUsageMetadataVo usageMetadata;
  private String modelVersion;
}
