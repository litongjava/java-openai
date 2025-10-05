package com.litongjava.gemini;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tools 数组的单个元素 { "function_declarations": [...] }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiToolVo {
  private List<GeminiFunctionDeclarationVo> function_declarations;
  private Map<String, Object> googleSearch;

  public GeminiToolVo enableSearch() {
    this.googleSearch = new HashMap<>(0);
    return this;
  }

  public GeminiToolVo disableSearch() {
    this.googleSearch = null;
    return this;
  }

  public GeminiToolVo(List<GeminiFunctionDeclarationVo> function_declarations) {
    this.function_declarations = function_declarations;
  }
}
