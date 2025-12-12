package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选答案
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiCandidate {
  private Integer index;
  private GeminiContentResponse content;
  private String finishReason;
  private Double avgLogprobs;
  private GroundingMetadata groundingMetadata;
}
