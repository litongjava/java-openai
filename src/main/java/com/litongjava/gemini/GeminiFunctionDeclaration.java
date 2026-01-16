package com.litongjava.gemini;

import java.util.Map;

/**
 * 用来表示单个 function_declaration
 */
public class GeminiFunctionDeclaration {
  private String name;
  private String description;
  // 注意示例里 parameters 是一个 JSON 对象，有 type, properties 等
  // 可以做更复杂的结构，但为了简化，这里用 Map<String,Object>。
  private Map<String, Object> parameters;

  public GeminiFunctionDeclaration() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiFunctionDeclaration(String name, String description, Map<String, Object> parameters) {
    super();
    this.name = name;
    this.description = description;
    this.parameters = parameters;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

}
