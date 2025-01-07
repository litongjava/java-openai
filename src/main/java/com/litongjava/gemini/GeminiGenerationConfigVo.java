package com.litongjava.gemini;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本生成配置
 * 例如:
 *   "generationConfig": {
 *       "stopSequences": ["Title"],
 *       "temperature": 1.0,
 *       "maxOutputTokens": 800,
 *       "topP": 0.8,
 *       "topK": 10
 *   }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiGenerationConfigVo {
  private List<String> stopSequences;
  private Double temperature;
  private Integer maxOutputTokens;
  private Double topP;
  private Integer topK;
}
