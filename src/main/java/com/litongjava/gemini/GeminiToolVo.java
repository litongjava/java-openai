package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tools 数组的单个元素
 * {
 *   "function_declarations": [...]
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiToolVo {
  private List<GeminiFunctionDeclarationVo> function_declarations;
}
