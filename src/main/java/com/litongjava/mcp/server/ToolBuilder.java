package com.litongjava.mcp.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.litongjava.mcp.model.McpContent;

/**
 * 工具构建器
 */

public class ToolBuilder {
  private final RegisteredTool tool = new RegisteredTool();
  private final Map<String, PropertyBuilder> properties = new LinkedHashMap<>();
  private final List<String> required = new ArrayList<>();

  public ToolBuilder(String name) {
    tool.setName(name);
  }

  public ToolBuilder title(String title) {
    tool.setTitle(title);
    return this;
  }

  public ToolBuilder description(String description) {
    tool.setDescription(description);
    return this;
  }

  /**
   * 添加字符串属性
   */
  public ToolBuilder addStringProperty(String name, String title, boolean required) {
    PropertyBuilder prop = new PropertyBuilder().type("string").title(title);
    properties.put(name, prop);
    if (required) {
      this.required.add(name);
    }
    return this;
  }

  /**
   * 添加整数属性
   */
  public ToolBuilder addIntegerProperty(String name, String title, Integer defaultValue, boolean required) {
    PropertyBuilder prop = new PropertyBuilder().type("integer").title(title).defaultValue(defaultValue);
    properties.put(name, prop);
    if (required) {
      this.required.add(name);
    }
    return this;
  }

  /**
   * 添加布尔属性
   */
  public ToolBuilder addBooleanProperty(String name, String title, Boolean defaultValue, boolean required) {
    PropertyBuilder prop = new PropertyBuilder().type("boolean").title(title).defaultValue(defaultValue);
    properties.put(name, prop);
    if (required) {
      this.required.add(name);
    }
    return this;
  }

  /**
   * 添加可空属性
   */
  public ToolBuilder addNullableProperty(String name, String title, String type, Object defaultValue,
      boolean required) {
    PropertyBuilder prop = new PropertyBuilder().nullable(type).title(title).defaultValue(defaultValue);
    properties.put(name, prop);
    if (required) {
      this.required.add(name);
    }
    return this;
  }

  /**
   * 添加可空对象属性
   */
  public ToolBuilder addNullableObjectProperty(String name, String title, boolean additionalProperties,
      Object defaultValue, boolean required) {
    PropertyBuilder prop = new PropertyBuilder().nullableObject(additionalProperties).title(title)
        .defaultValue(defaultValue);
    properties.put(name, prop);
    if (required) {
      this.required.add(name);
    }
    return this;
  }

  /**
   * 设置处理函数
   */
  public ToolBuilder handler(Function<Map<String, Object>, List<McpContent>> handler) {
    tool.setHandler(handler);
    return this;
  }

  /**
   * 构建工具
   */
  public RegisteredTool build() {
    // 构建 inputSchema
    Map<String, Object> schema = new LinkedHashMap<>();
    schema.put("title", tool.getName() + "Arguments");
    schema.put("type", "object");

    // 构建 properties
    Map<String, Object> propsMap = new LinkedHashMap<>();
    for (Map.Entry<String, PropertyBuilder> entry : properties.entrySet()) {
      propsMap.put(entry.getKey(), entry.getValue().build());
    }
    schema.put("properties", propsMap);

    // 添加 required
    if (!required.isEmpty()) {
      schema.put("required", required);
    }

    tool.setInputSchema(schema);
    return tool;
  }

}