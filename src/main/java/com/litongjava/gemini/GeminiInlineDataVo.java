package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 如果是图片等非文本内容，需要用 inline_data 封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiInlineDataVo {
  private String mimeType;
  private String data; // base64编码后的二进制
}
