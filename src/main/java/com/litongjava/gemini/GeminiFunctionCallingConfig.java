package com.litongjava.gemini;

import java.util.List;

/**
 * function_calling_config 结构 { "mode": "ANY", "allowed_function_names": [
 * "enable_lights", "set_light_color", "stop_lights" ] }
 */
public class GeminiFunctionCallingConfig {
  private String mode;

  private List<String> allowed_function_names;

  public GeminiFunctionCallingConfig() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiFunctionCallingConfig(String mode, List<String> allowed_function_names) {
    super();
    this.mode = mode;
    this.allowed_function_names = allowed_function_names;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public List<String> getAllowed_function_names() {
    return allowed_function_names;
  }

  public void setAllowed_function_names(List<String> allowed_function_names) {
    this.allowed_function_names = allowed_function_names;
  }
}
