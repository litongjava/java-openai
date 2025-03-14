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
public class GeminiCandidateVo {
  private GeminiContentResponseVo content;
  private String finishReason;
  private Double avgLogprobs;
}
