package com.litongjava.gemini;

import java.util.List;

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
  private int cachedContentTokenCount;
  private int thoughtsTokenCount;
  private List<GeminipTokensDetail> promptTokensDetails;
  private List<GeminipTokensDetail> cacheTokensDetails;
}
