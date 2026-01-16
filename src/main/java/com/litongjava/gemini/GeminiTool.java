package com.litongjava.gemini;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tools 数组的单个元素 { "function_declarations": [...] }
 */
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

  public GeminiTool() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiTool(List<GeminiFunctionDeclaration> function_declarations, Map<String, Object> googleSearch) {
    super();
    this.function_declarations = function_declarations;
    this.googleSearch = googleSearch;
  }

  public List<GeminiFunctionDeclaration> getFunction_declarations() {
    return function_declarations;
  }

  public void setFunction_declarations(List<GeminiFunctionDeclaration> function_declarations) {
    this.function_declarations = function_declarations;
  }

  public Map<String, Object> getGoogleSearch() {
    return googleSearch;
  }

  public void setGoogleSearch(Map<String, Object> googleSearch) {
    this.googleSearch = googleSearch;
  }

}
