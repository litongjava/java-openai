package com.litongjava.gemini;

import java.util.List;

import com.litongjava.chat.UniResponseSchema;
import com.litongjava.chat.UniThinkingConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文本生成配置
 * 例如:
 *   "generationConfig": {
 *       "stopSequences": ["Title"],
 *       "temperature": 0,
 *       "maxOutputTokens": 800,
 *       "topP": 0.8,
 *       "topK": 10
 *   }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GeminiGenerationConfig {
  private Float temperature;
  private Integer topK;
  private Double topP;
  private Integer maxOutputTokens;
  private List<String> stopSequences;
  private List<String> responseModalities;
  private String responseMimeType;
  private UniResponseSchema responseSchema;
  private UniThinkingConfig thinkingConfig;

  public GeminiGenerationConfig buildJsonValue() {
    return this.setResponseMimeType("application/json");
  }
}
