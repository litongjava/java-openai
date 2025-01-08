package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示 system_instruction 结构
 * 例如:
 * {
 *   "system_instruction": {
 *     "parts": {
 *       "text": "You are a helpful lighting system bot..."
 *     }
 *   }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiSystemInstructionVo {
  /**
   * 这里为了和你示例中的 JSON 对应，里层是一个 parts 对象，
   * 而 parts 本身也可能是 text/image/functionCall 等。
   * 你可以根据实际需要，直接用 GeminiPartVo 或别的包装方式。
   */
  private GeminiPartVo parts;
}
