package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * function_calling_config 结构
 * {
 *   "mode": "ANY",
 *   "allowed_function_names": [
 *     "enable_lights",
 *     "set_light_color",
 *     "stop_lights"
 *   ]
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiFunctionCallingConfig {
  private String mode;

  private List<String> allowed_function_names;
}
