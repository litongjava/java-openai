package com.litongjava.gemini;

/**
 * "tool_config": { "function_calling_config": { ... } }
 */
public class GeminiToolConfig {

  private GeminiFunctionCallingConfig function_calling_config;

  public GeminiToolConfig() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiToolConfig(GeminiFunctionCallingConfig function_calling_config) {
    super();
    this.function_calling_config = function_calling_config;
  }

  public GeminiFunctionCallingConfig getFunction_calling_config() {
    return function_calling_config;
  }

  public void setFunction_calling_config(GeminiFunctionCallingConfig function_calling_config) {
    this.function_calling_config = function_calling_config;
  }

}
