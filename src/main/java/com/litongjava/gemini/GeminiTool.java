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
public class GeminiTool {
  private List<GeminiFunctionDeclaration> function_declarations;
  private Map<String, Object> googleSearch;

  public GeminiTool enableSearch() {
    this.googleSearch = new HashMap<>(0);
    return this;
  }

  public GeminiTool disableSearch() {
    this.googleSearch = null;
    return this;
  }

  public GeminiTool(List<GeminiFunctionDeclaration> function_declarations) {
    this.function_declarations = function_declarations;
  }
}
