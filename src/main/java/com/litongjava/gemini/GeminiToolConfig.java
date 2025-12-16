package com.litongjava.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "tool_config": {
 *   "function_calling_config": { ... }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiToolConfig {

  private GeminiFunctionCallingConfig function_calling_config;
}
