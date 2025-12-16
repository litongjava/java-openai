package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全策略
 * 例如:
 *   {
 *     "category": "HARM_CATEGORY_DANGEROUS_CONTENT",
 *     "threshold": "BLOCK_ONLY_HIGH"
 *   }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiSafetySetting {
  private String category;
  private String threshold;
}
