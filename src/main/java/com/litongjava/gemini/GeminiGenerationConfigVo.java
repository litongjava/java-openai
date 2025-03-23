package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class GeminiGenerationConfigVo {
  private Double temperature;
  private Integer topK;
  private Double topP;
  private Integer maxOutputTokens;
  private List<String> stopSequences;
  private String responseMimeType;
  private GeminiResponseSchema responseSchema;

  public GeminiGenerationConfigVo buildJsonValue() {
    return this.setTemperature(1d).setTopK(40).setTopP(0.95).setMaxOutputTokens(8192).setResponseMimeType("application/json");
  }
}
