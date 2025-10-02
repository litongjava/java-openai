package com.litongjava.mcp.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性构建器
 */
public class PropertyBuilder {
  private String type;
  private String title;
  private Object defaultValue;
  private boolean isNullable;
  private Boolean additionalProperties;
  private boolean isNullableObject;

  public PropertyBuilder type(String type) {
    this.type = type;
    return this;
  }

  public PropertyBuilder title(String title) {
    this.title = title;
    return this;
  }

  public PropertyBuilder defaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  public PropertyBuilder nullable(String baseType) {
    this.isNullable = true;
    this.type = baseType;
    return this;
  }

  public PropertyBuilder additionalProperties(boolean additionalProperties) {
    this.additionalProperties = additionalProperties;
    return this;
  }

  public PropertyBuilder nullableObject(boolean additionalProperties) {
    this.isNullableObject = true;
    this.additionalProperties = additionalProperties;
    return this;
  }

  public Map<String, Object> build() {
    Map<String, Object> prop = new LinkedHashMap<>();

    if (isNullableObject) {
      // 构建可空对象的 anyOf 结构
      List<Map<String, Object>> anyOf = new ArrayList<>();

      Map<String, Object> objectMap = new LinkedHashMap<>();
      objectMap.put("type", "object");
      if (additionalProperties != null) {
        objectMap.put("additionalProperties", additionalProperties);
      }
      anyOf.add(objectMap);

      Map<String, Object> nullMap = new LinkedHashMap<>();
      nullMap.put("type", "null");
      anyOf.add(nullMap);

      prop.put("anyOf", anyOf);
    } else if (isNullable) {
      // 构建普通类型的 anyOf 结构
      List<Map<String, Object>> anyOf = new ArrayList<>();
      Map<String, Object> typeMap = new LinkedHashMap<>();
      typeMap.put("type", type);
      anyOf.add(typeMap);

      Map<String, Object> nullMap = new LinkedHashMap<>();
      nullMap.put("type", "null");
      anyOf.add(nullMap);

      prop.put("anyOf", anyOf);
    } else {
      prop.put("type", type);
      if ("object".equals(type) && additionalProperties != null) {
        prop.put("additionalProperties", additionalProperties);
      }
    }

    if (title != null) {
      prop.put("title", title);
    }

    if (defaultValue != null) {
      prop.put("default", defaultValue);
    }

    return prop;
  }
}