package com.litongjava.gemini;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来表示单个 function_declaration
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiFunctionDeclarationVo {
  private String name;
  private String description;
  // 注意示例里 parameters 是一个 JSON 对象，有 type, properties 等
  // 可以做更复杂的结构，但为了简化，这里用 Map<String,Object>。
  private Map<String, Object> parameters;
}
