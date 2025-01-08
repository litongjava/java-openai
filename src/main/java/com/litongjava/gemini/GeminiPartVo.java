package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对话的一段内容
 * 既可以是 text，也可以是 image 等
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiPartVo {
  private String text;
  private GeminiInlineDataVo inline_data;
  private GeminiFunctionCallVo functionCall;
}
